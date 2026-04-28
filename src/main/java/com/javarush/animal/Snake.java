package com.javarush.animal;

import com.javarush.config.SimulationConfig;
import com.javarush.model.Island;
import com.javarush.model.Location;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Snake extends Animal {


    private static final double WEIGHT = 15;
    private static final double MAX_SATIETY = 3;
    private static final double SPEED = 1;


    public Snake() {
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


