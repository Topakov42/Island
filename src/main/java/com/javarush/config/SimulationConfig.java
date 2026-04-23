package com.javarush.config;

import com.javarush.animal.*;
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
    private int islandWidth = 50;
    private int islandHeight = 50;
    // Популяции

    private int initialWolf = 300;
    private int initialRabbit = 1500;
    private int initialDeer = 200;
    private int initSnake = 300;
    private int initFox = 300;
    private int initBear = 50;
    private int initEagle = 200;
    private int initHorse = 200;
    private int initMouse = 5000;
    private int initGoat = 1400;
    private int initSheep = 1400;
    private int initWild = 500;
    private int initDuck = 2000;
    private int initCaterpillar = 10_000;

    private double hungryTick = 0.30;

    private double defaultValue = 15; // количество зелени по дефолту
    private int plantsPerCell = 5; // кол-во растение которые добавляются за 1 такт в каждую клетку.

    // Дюрация в мс.
    private long ticketDurationMs = 1000;


    /*todo придумать какие нибудь кусты\деревья (при которых шанс выживания тровоядных микрочеликов повышается)
     */


    private Map<Class<? extends Animal>, Map<Class<? extends Animal>, Integer>> mapEating = mapEatingInitialize(); // мапа вероятностей поедания животных
    private Map<Class<? extends Animal>, Integer> MaxAnimalsPerCell = MaxCountAnimals(); // максимальное количество животных


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
        if (object instanceof Snake) {
            return initSnake;
        }
        if (object instanceof Fox) {
            return initFox;
        }
        if (object instanceof Bear) {
            return initBear;
        }
        if (object instanceof Eagle) {
            return initEagle;
        }
        if (object instanceof Horse) {
            return initHorse;
        }
        if (object instanceof Mouse) {
            return initMouse;
        }
        if (object instanceof Goat) {
            return initGoat;
        }
        if (object instanceof Sheep) {
            return initSheep;
        }
        if (object instanceof Wild) {
            return initWild;
        }
        if (object instanceof Duck) {
            return initDuck;
        }
        if (object instanceof Caterpillar) {
            return initCaterpillar;
        }
        return 0;
    }


    public Map<Class<? extends Animal>, Integer> MaxCountAnimals() {
        Map<Class<? extends Animal>, Integer> mapMax = new HashMap<>();
        mapMax.put(Rabbit.class, 150);
        mapMax.put(Wolf.class, 30);
        mapMax.put(Deer.class, 20);
        mapMax.put(Snake.class, 30);
        mapMax.put(Fox.class, 30);
        mapMax.put(Bear.class, 5);
        mapMax.put(Eagle.class, 20);
        mapMax.put(Horse.class, 20);
        mapMax.put(Mouse.class, 500);
        mapMax.put(Goat.class, 140);
        mapMax.put(Sheep.class, 140);
        mapMax.put(Wild.class, 50);
        mapMax.put(Duck.class, 200);
        mapMax.put(Caterpillar.class, 1000);
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
