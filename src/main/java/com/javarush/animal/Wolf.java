package com.javarush.animal;

import com.javarush.model.Island;
import com.javarush.model.Location;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Wolf extends Animal {

    private static final double WEIGHT = 50;
    //    private static final double MAX_COUNT_PER_CELL = 30;
    //    private static final double SPEED = 3;
    private static final double MAX_SATIETY = 8;


    private static final Map<Class<? extends Animal>, Integer> EATING_PROBABILITIES = Map.of(Rabbit.class, 60, Deer.class, 15); //todo  заполнить карту. еще бы понять как ее заполнять ((.


    public Wolf() {
        super(WEIGHT, MAX_SATIETY);
        this.eatingProbabilities = EATING_PROBABILITIES;
    }

    @Override
    public void eat(Location location) {
// жив ли объект?
        if (!alive) {
            return;
        }
        for (Animal prey : location.getAnimals()) {
            if (prey == this || !prey.isAlive()) continue;
            Integer prob = eatingProbabilities.get(prey.getClass());
            if (prob != null && ThreadLocalRandom.current().nextInt(100) < prob) {
                location.removeAnimal(prey);
                prey.die();
                currentSatiety = Math.min(maxSatiety, currentSatiety + prey.getWeight());
                log.debug("Волк съел {} ", prey.getClass().getSimpleName());
                break;
            }
        }
    }

    @Override
    public void move(Island island, int currentX, int currentY) {
//ЗАГЛУШКА
    }

    @Override
    public void reproduce(Location location) {
// ЗАГЛУШКА
    }
}
