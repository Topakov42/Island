package com.javarush.model;

import com.javarush.animal.Animal;
import com.javarush.animal.Deer;
import com.javarush.animal.Rabbit;
import com.javarush.animal.Wolf;
import com.javarush.config.SimulationConfig;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;


public class Location {
    @Getter
    private final List<Animal> animals = new CopyOnWriteArrayList<>();  // многопоточность (1)
    private final List<Plant> plants = new CopyOnWriteArrayList<>();


    public void addAnimal(Animal animal) {
        animals.add(animal);
        animal.setCurrentLocation(this);

    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }


    public Plant removePlant() {
        synchronized (plants) {  // многопоточность (2)
            if (!plants.isEmpty()) {
                return plants.remove(plants.size() - 1);
            }
            return null;
        }
    }

    public static int valueAnimalPerCel(Object object, SimulationConfig config, Location location) {
        Animal animalType = config.animalInit(object);
        int valueAnimal = 0;
        for (Animal anim : location.getAnimals()) {
            if (anim.getClass().equals(animalType.getClass())) {
                valueAnimal++;
            }
        }
        return valueAnimal;
    }


    public List<Plant> getPlants() {
        return plants;
    }
}
