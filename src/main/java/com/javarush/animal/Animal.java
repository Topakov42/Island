package com.javarush.animal;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.javarush.config.SimulationConfig;
import com.javarush.model.Location;
import com.javarush.model.Island;
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
    //volatile  гарантируем что все потоки увидят актуальное значение
    protected volatile Location currentLocation; // текущее местонахождение животного



    // Карта вероятности поедания других животных

    protected Map<Class<? extends Animal>, Integer> eatingProbabilities;

    public Animal(double weight, double maxSatiety)  {
        this.weight = weight;
        this.maxSatiety = maxSatiety;
        this.currentSatiety = maxSatiety;
    }

    // eat , move , reproduce


    public abstract void eat(Location location, SimulationConfig config);

    public void move(Island island, int currentX, int currentY) {
        if (!alive) {
            return;
        }
        if (currentLocation == null) {
            log.warn("Животное {} не имеет текущей локации. Перемещение не возможно", this);
            return;
        }
        int diriction = ThreadLocalRandom.current().nextInt(4);
        int newX = currentX;
        int newY = currentY;

        //0 .. 3

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

    /**
     * Многопоточный метод reproduce
     *
     * @param location
     */



    public void reproduce(Location location) {
        if (!alive) {
            return;
        }
        // подсчет особей того же вида (фильтруем только живых и того же класса , кроме самого себя  )

        long sameSpeciesCount = location.getAnimals().stream()
                .filter(a -> a.getClass() == this.getClass() && a != this && a.isAlive()) // промежуточная операция ( фильтрация)
                .count();  // терминальная

        // Условия для размножения животных: Наличие хотя бы 1 особей того же вида (sameSpeciesCount ), шанс размножения
        if (sameSpeciesCount > 0 && ThreadLocalRandom.current().nextInt(100) < 30) {


            // создание потомка через рефлексию (не требуется значение конкретного подкласса во время компиляции)
            try {
                Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                baby.setCurrentSatiety(baby.getMaxSatiety() / 2); //установка начальной сытости - как половинка от максимального значения
                location.addAnimal(baby);  // родившееся животное добавляем в локацию
                log.info("Родилось животное {}", baby.getClass().getSimpleName());
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


}
