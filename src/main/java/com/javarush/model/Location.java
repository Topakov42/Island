package com.javarush.model;

import com.javarush.animal.Animal;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Класс Локация содержит списки животных и объектов.
 * todo записать прогу для многопоточки!!!
 * Метод removePlant удаляет последнее растение без синххронизации (смотреть туду)
 */
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


    // Ментор сказал - для однопоточной версии. Как то надо будет развить по для многопоточки
    public Plant removePlant() {
        synchronized (plants) {  // многопоточность (2)
            if (!plants.isEmpty()) {
                return plants.remove(plants.size() - 1);
            }
            return null;
        }
    }

    public List <Plant>getPlants () {
        return plants;
    }
}
