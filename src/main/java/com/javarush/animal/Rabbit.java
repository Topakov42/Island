package com.javarush.animal;

import com.javarush.config.SimulationConfig;
import com.javarush.model.Island;
import com.javarush.model.Location;
import com.javarush.model.Plant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Rabbit extends Animal{

    private static final double WEIGHT = 2;
    private static final double MAX_SATIETY = 0.45;


    public Rabbit() {
        super(WEIGHT, MAX_SATIETY);
    }



    @Override
    public void eat(Location location, SimulationConfig config) {
        if (!alive) {
            return;
        }
        Plant plant = location.removePlant();
        if (plant != null) {
            currentSatiety = Math.min(maxSatiety, currentSatiety + plant.getWeight());
            log.debug("Кролик съел  растение");
        }

    }

    @Override
    public void move(Island island, int currentX, int currentY) {

    }

    @Override
    public void reproduce(Location location) {
        super.reproduce(location);
    }
}
