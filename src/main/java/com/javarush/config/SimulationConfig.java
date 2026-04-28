package com.javarush.config;

import com.javarush.animal.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация приложения
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulationConfig {
    //размеры острова
    private int islandWidth = 50;
    private int islandHeight = 50;
    // Популяции

    private int initWolf; //= 300;
    private int initRabbit;// = 1500;
    private int initDeer;// = 200;
    private int initSnake;// = 300;
    private int initFox;// = 300;
    private int initBear;// = 50;
    private int initEagle;// = 200;
    private int initHorse;// = 200;
    private int initMouse;// = 5000;
    private int initGoat;// = 1400;
    private int initSheep;// = 1400;
    private int initWild;// = 500;
    private int initDuck;// = 2000;
    private int initCaterpillar;// = 10_000;

    private double hungryTick;// = 0.30;

    private int defaultInitPlant; // = 15; // количество зелени по дефолту
    private int plantsPerCell; // = 5; // кол-во растение которые добавляются за 1 такт в каждую клетку.

    // Дюрация в мс.
    private long ticketDurationMs; // = 1000;


    private Map<Class<? extends Animal>, Map<Class<? extends Animal>, Integer>> mapEating = mapEatingInitialize(); // мапа вероятностей поедания животных
    private Map<Class<? extends Animal>, Integer> MaxAnimalsPerCell = MaxCountAnimals();// максимальное количество животных

    private Map<Class<? extends Animal>, Map<Class<? extends Animal>, Integer>> mapEatingInitialize() {
        Map<Class<? extends Animal>, Map<Class<? extends Animal>, Integer>> map = new HashMap<>();
        Map<Class<? extends Animal>, Integer> wolfMap = Map.of(Rabbit.class, 60, Deer.class, 15, Horse.class, 10, Mouse.class, 80, Goat.class, 60, Sheep.class, 70, Wild.class, 15, Duck.class, 40);
        Map<Class<? extends Animal>, Integer> snakefMap = Map.of(Fox.class, 15, Rabbit.class, 20, Mouse.class, 40, Duck.class, 10);
        Map<Class<? extends Animal>, Integer> foxMap = Map.of(Rabbit.class, 70, Mouse.class, 90, Duck.class, 60, Caterpillar.class, 40 );
        Map<Class<? extends Animal>, Integer> bearfMap = Map.of(Snake.class, 80, Horse.class, 40, Deer.class, 80, Rabbit.class, 80 , Mouse.class, 90, Goat.class, 70, Sheep.class, 70, Wild.class, 50, Duck.class, 10 );
        Map<Class<? extends Animal>, Integer> eaglMap = Map.of(Fox.class, 10, Rabbit.class, 90, Mouse.class, 90, Duck.class, 90);
        Map<Class<? extends Animal>, Integer> mouseMap = Map.of(Caterpillar.class, 90);
        Map<Class<? extends Animal>, Integer> wildMap = Map.of(Mouse.class, 50, Caterpillar.class, 90);
        Map<Class<? extends Animal>, Integer> duckMap = Map.of(Caterpillar.class, 90);
        map.put(Wolf.class, wolfMap);
        map.put(Snake.class, snakefMap);
        map.put(Fox.class, foxMap);
        map.put(Bear.class, bearfMap);
        map.put(Eagle.class, eaglMap);
        map.put(Mouse.class, mouseMap);
        map.put(Wild.class, wildMap);
        map.put(Duck.class, duckMap);
        return map;
    }

    public static SimulationConfig initConfig () {
        String path = "/home/topakov/Documents/Project/island/src/main/resources/application.yaml";
        try {
            SimulationConfig yaml = new Yaml().loadAs(new FileInputStream(path), SimulationConfig.class);
            return yaml;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public int getAnimalValue(Object object) {

        if (object instanceof Rabbit) {
            return initRabbit;
        }
        if (object instanceof Wolf) {
            return initWolf;
        }
        if (object instanceof Deer) {
            return initDeer;
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
//
//    public Animal animalInit(Object object) {
//        if (object instanceof Rabbit) {
//            return new Rabbit();
//        }
//        if (object instanceof Wolf) {
//            return new Wolf();
//        }
//        if (object instanceof Deer) {
//            return new Deer();
//        }
//        if (object instanceof Snake) {
//            return new Snake();
//        }
//        if (object instanceof Fox) {
//            return new Fox();
//        }
//        if (object instanceof Bear) {
//            return new Bear();
//        }
//        if (object instanceof Eagle) {
//            return new Eagle();
//        }
//        if (object instanceof Horse) {
//            return new Horse();
//        }
//        if (object instanceof Mouse) {
//            return new Mouse();
//        }
//        if (object instanceof Goat) {
//            return new Goat();
//        }
//        if (object instanceof Sheep) {
//            return new Sheep();
//        }
//        if (object instanceof Wild) {
//            return new Wild();
//        }
//        if (object instanceof Duck) {
//            return new Duck();
//        }
//        if (object instanceof Caterpillar) {
//            return new Caterpillar();
//        }
//        return null;
//    }


    public Animal animalInit(Object object) {
        try {
            return (Animal) object.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

}
