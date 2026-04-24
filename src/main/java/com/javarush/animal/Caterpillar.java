package com.javarush.animal;

import com.javarush.config.SimulationConfig;
import com.javarush.model.Island;
import com.javarush.model.Location;

public class Caterpillar extends Animal {


    private static final double WEIGHT = 0.01;
    private static final double MAX_SATIETY = 0;
    private static final double SPEED = 0;

    public Caterpillar() {
        super(WEIGHT, MAX_SATIETY, SPEED);
    }


    @Override
    public void eat(Location location, SimulationConfig config) {
        super.eatHerbivore(location, config);
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

