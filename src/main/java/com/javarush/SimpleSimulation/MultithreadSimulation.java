package com.javarush.SimpleSimulation;

import com.javarush.animal.Animal;
import com.javarush.animal.Deer;
import com.javarush.animal.Rabbit;
import com.javarush.animal.Wolf;
import com.javarush.config.SimulationConfig;
import com.javarush.model.Island;
import com.javarush.model.Location;
import com.javarush.model.Plant;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;

@Slf4j

public class MultithreadSimulation {
    private static final int CORE_POLL_SIZE = 1;
    private static final int THREADS = 10;

    private final Island island;
    private final SimulationConfig config;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(CORE_POLL_SIZE);
    private final ExecutorService workerPool = Executors.newFixedThreadPool(THREADS);
    private volatile boolean running = true;

    public MultithreadSimulation(SimulationConfig config) {
        this.island = new Island(config.getIslandWidth(), config.getIslandHeight());
        this.config = config;
    }


    public void initialize() {
        List<Animal> animals = List.of(new Wolf(), new Rabbit(), new Deer());
        for (Animal a : animals) {
            initializeAnimal(a);
        }
        // Растения
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);

                for (int p = 0; p < config.getDefaultValue(); p++) { // t odo вынести в настройки  -  готово
                    location.addPlant(new Plant());
                }
            }
        }
        log.info("инициализация завершена, животные и растения размещены");
    }


    public void initializeAnimal(Object object) {
        for (int i = 0; i < config.getAnimalValue(object); i++) {
            int x = ThreadLocalRandom.current().nextInt(config.getIslandWidth());
            int y = ThreadLocalRandom.current().nextInt(config.getIslandHeight());

            Animal animal = config.animalInit(object);
            if (config.MaxCountAnimals().get(animal.getClass()) > Location.valueAnimalPerCel(animal, config, island.getLocation(x, y))) {
                island.getLocation(x, y).addAnimal(animal);
            }
        }
    }


    private void tick() {
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                for (int i = 0; i < Plant.plantGrowthRate(); i++) {
                    location.addPlant(new Plant());
                }
            }
        }

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                int finalX = x;
                int finalY = y;
                for (Animal animal : location.getAnimals()) {
                    if (!animal.isAlive()) {
                        continue;
                    }
                    tasks.add(() -> {
                        animal.eat(animal.getCurrentLocation(), config);
                        animal.move(island, finalX, finalY, animal.getSpeed());
                        if (config.MaxCountAnimals().get(animal.getClass()) > Location
                                .valueAnimalPerCel(animal, config, animal.getCurrentLocation())) {  // высчитываем кол-во особей на клетке
                            animal.reproduce(animal.getCurrentLocation());
                        }

                        animal.setCurrentSatiety(Math.floor(animal.getMaxSatiety() * 0.7 * 1000) / 1000);

                        if (animal.getCurrentSatiety() <= 0) {
                            animal.die();
                            animal.getCurrentLocation().removeAnimal(animal);
                        }
                        return null;
                    });
                }
            }
        }

        try {
            List<Future<Void>> futures = workerPool.invokeAll(tasks);  // Отправляем действия в пул рабочих потоков
            for (Future<Void> f : futures) {
                f.get();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Такт прерван");
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.error("Ошибка при выполнении задачи животного", e.getCause());
        }


        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                for (Animal animal : location.getAnimals()) {
                    animal.yesReproduce();
                }
            }
        }

        //todo в этом месте мы меняем все флаги репродукции

        printStatistics();
    }


    public void printStatistics() {
        int wolves = 0;
        int rabbit = 0;
        int deer = 0;
        int plants = 0;
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                for (Animal animal : location.getAnimals()) {
                    if (animal instanceof Wolf) wolves++;
                    else if (animal instanceof Rabbit) rabbit++;
                    else if (animal instanceof Deer) deer++;
                }
                plants += location.getPlants().size();
            }
        }


        log.info(" Статистика : Волки = {}; Кролики = {} ; Олени = {} ;  Растения {}", wolves, rabbit, deer, plants);
    }


    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (running) {
                tick();
            }
        }, 0, config.getTicketDurationMs(), TimeUnit.MILLISECONDS);
        log.info("Симуляция запущена с тактом {}, мс", config.getTicketDurationMs());
    }


    public void stop() {
        running = false;
        scheduledExecutorService.shutdown();
        workerPool.shutdown();
        log.info("Симуляция остановлена");

    }

}
