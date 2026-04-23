package com.javarush.config;

import com.javarush.animal.Animal;
import com.javarush.animal.Deer;
import com.javarush.animal.Rabbit;
import com.javarush.animal.Wolf;
import com.javarush.model.Island;
import com.javarush.model.Location;
import com.javarush.model.Plant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Конфигурация приложения
 */


@Data
@Builder //  Протиать про Lombok( Паттерн Билдер )
@NoArgsConstructor
@AllArgsConstructor
public class SimulationConfig {
    //размеры острова
    private int islandWidth = 10;
    private int islandHeight = 10;
    // Популяции
    private int initialWolf = 10;
    private int initialRabbit = 100;
    private int initialDeer = 30;
    private double defaultValue = 7; // количество зелени по дефолту
    private Map<Class<? extends Animal>, Map<Class<? extends Animal>, Integer>> mapEating = mapEatingInitialize();
    private Map<Class<? extends Animal>, Integer> MaxAnimalsPerCell = MaxCountAnimals();

    /*todo придумать какие нибудь кусты\деревья (при которых шанс выживания тровоядных микрочеликов повышается)
     */


    private int plantsPerCell = 5; // кол-во растение которые добавляются за 1 такт в каждую клетку.

    // Дюрация в мс.
    private long ticketDurationMs = 1000;

    private Map<Class<? extends Animal>, Map<Class<? extends Animal>, Integer>> mapEatingInitialize() {
        Map<Class<? extends Animal>, Map<Class<? extends Animal>, Integer>> map = new HashMap<>();
        Map<Class<? extends Animal>, Integer> wolfMap = Map.of(Rabbit.class, 60, Deer.class, 15);
        map.put(Wolf.class, wolfMap);
        return map;
    }


    public int getAnimalValue(Object object) {
        if (object instanceof Rabbit) {
            return initialRabbit;
        }
        if (object instanceof Wolf) {
            return initialWolf;
        }
        if (object instanceof Deer) {
            return initialDeer;
        }
        return 0;
    }

    public Map<Class<? extends Animal>, Integer> MaxCountAnimals () {
        Map<Class<? extends Animal>, Integer> mapMax = new HashMap<>();
        mapMax.put(Rabbit.class, 150);
        mapMax.put(Wolf.class, 30);
        mapMax.put(Deer.class , 20);

        return mapMax;
    }



    public Animal animalInit(Object object) {
        if (object instanceof Rabbit) {
            return new Rabbit();
        }
        if (object instanceof Wolf) {
            return new Wolf();
        }
        if (object instanceof Deer) {
            return new Deer();
        }
        return null;
    }


}
