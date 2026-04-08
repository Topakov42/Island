package com.javarush.model;

import com.javarush.animal.Animal;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


/**
 * Класс Локация содержит списки животных и объектов.
 * todo записать прогу для многопоточки!!!
 * Метод removePlant удаляет последнее растение без синххронизации (смотреть туду)
 */
public class Location {


    @Getter
    private final List<Animal> animals = new ArrayList<>();

    @Getter
    private final List<Plant> plants = new ArrayList<>();

    public void addAnimal (Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal (Animal animal) {
        animals.remove(animal);
    }

    public void addPlant (Plant plant ) {
        plants.add (plant);
    }


    // Ментор сказал - для однопоточной версии. Как то надо будет развить по для многопоточки
    public Plant removePlant () {
        if (!plants.isEmpty()) {
            return plants.remove(plants.size() -1 );
        }
        return null;
    }

}
