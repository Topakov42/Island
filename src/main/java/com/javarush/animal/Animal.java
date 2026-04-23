package com.javarush.animal;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.javarush.config.SimulationConfig;
import com.javarush.model.Location;
import com.javarush.model.Island;
import com.javarush.model.Plant;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j

public abstract class Animal {
    private static final int CHANCE_OF_REPRODUCE = 30; // шанс разможения
    protected double weight;  // вес
    protected double maxSatiety; // сытость максимальная
    protected double currentSatiety; // текущая сытость
    protected boolean alive = true;  // статус жизни животного
    protected double speed; // скорость перемещения
    protected boolean hasReproduced = true; // готов к размножению
    //volatile  гарантируем что все потоки увидят актуальное значение


    protected volatile Location currentLocation; // текущее местонахождение животного


    // Карта вероятности поедания других животных

    protected Map<Class<? extends Animal>, Integer> eatingProbabilities;

    public Animal(double weight, double maxSatiety, double speed) {
        this.weight = weight;
        this.maxSatiety = maxSatiety;
        this.currentSatiety = maxSatiety;
        this.speed = speed;
    }

    public abstract void eat(Location location, SimulationConfig config);

    public void move(Island island, int currentX, int currentY, double speedAnimal) {
        if (!alive) {
            return;
        }
        if (currentLocation == null) {
            log.warn("Животное {} не имеет текущей локации. Перемещение не возможно", this);
            return;
        }

        for (int s = 0; s <= speedAnimal; s++) {
            int diriction = ThreadLocalRandom.current().nextInt(4);
            int newX = currentX;
            int newY = currentY;
            switch (diriction) {
                case 0:
                    newY = Math.max(0, currentY - 1);
                    break;  // вверх Y
                case 1:
                    newX = Math.min(island.getWidth() - 1, currentX + 1);
                    break; // вправо X
                case 2:
                    newY = Math.min(island.getHeight() - 1, currentY + 1);
                    break; // вниз Y
                case 3:
                    newX = Math.max(0, currentX - 1);
                    break; // влево X
            }
        }
    }

    public void reproduce(Location location) {
        if (!alive || !hasReproduced) {
            return;
        }
        Animal samec = location.getAnimals().stream().
                filter(a ->a.getClass() == this.getClass() && a != this && a.isAlive() && a.hasReproduced)
                .findFirst().orElse(null);
        // Условия для размножения животных: Наличие хотя бы 1 особей того же вида (sameSpeciesCount ), шанс размножения
        if ((samec != null) && ThreadLocalRandom.current().nextInt(100) < CHANCE_OF_REPRODUCE) {

            try {
                Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                baby.setCurrentSatiety(baby.getMaxSatiety() / 2); //установка начальной сытости - как половинка от максимального значения
                location.addAnimal(baby);  // родившееся животное добавляем в локацию
                log.info("Родилось животное {}", baby.getClass().getSimpleName());
                this.hasReproduced = false;
                samec.hasReproduced = false;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                log.error("Ошибка при создании нового животного!");
                throw new RuntimeException(e);
            }
        }
    }

    public void die() {
        this.alive = false;
    }

    public void yesReproduce () {
        this.hasReproduced = true;
    }

    public void eatPredator(Location location, SimulationConfig config) {
        if (!alive || currentSatiety >= maxSatiety) {
            return;
        }

        for (Animal prey : location.getAnimals()) {
            if (prey == this || !prey.isAlive())
                continue;              // у живого - isAlive() - тру.  !prey.isAlive()) - проверяем что животное погибло. проверка что за животное
            Integer probability = config.getMapEating().get(Wolf.class).get(prey.getClass());   // заглядываем в мапу чтобы получить вероятность -  если это кролик - процент его съесть - 1 %
            if (probability != null && ThreadLocalRandom.current().nextInt(100) < probability) {  // если вероятность не равна 0 или вероятность быть съеденым больше рандома
                location.removeAnimal(prey);   // удаляем животное
                prey.die();  // ставим статус животному - умер
                currentSatiety = Math.min(maxSatiety, currentSatiety + prey.getWeight());  // текущая сытость - берем мин значение ( максимальная сытость, вес жертвы)
                log.info("Волк съел {} ", prey.getClass().getSimpleName());
                break;
            }
        }
    }

    public void eatHerbivore(Location location, SimulationConfig config) {
        if (!alive || currentSatiety >= maxSatiety) {
            return;
        }
        Plant plant = location.removePlant();
        if (plant != null) {
            currentSatiety = Math.min(maxSatiety, currentSatiety + plant.getWeight());
            log.debug("Кролик съел  растение");
        }
    }
}
