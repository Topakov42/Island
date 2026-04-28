package com.javarush.animal;

import com.javarush.config.SimulationConfig;
import com.javarush.model.Island;
import com.javarush.model.Location;

public class Bear extends Animal {


    private static final double WEIGHT = 500;
    private static final double MAX_SATIETY = 80;
    private static final double SPEED = 2;


    public Bear() {
        super(WEIGHT, MAX_SATIETY, SPEED);
    }

    @Override
    public void eat(Location location, SimulationConfig config) {
        super.eatPredator(location, config);
    }

    @Override
    public void move(Island island, int currentX, int currentY, double SPEED) {
        super.move(island, currentX, currentY, SPEED);
    }

    @Override
    public void reproduce(Location location) {
        super.reproduce(location);
    }
}



