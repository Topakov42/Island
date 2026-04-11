package com.javarush.animal;

import com.javarush.model.Island;
import com.javarush.model.Location;
import com.javarush.model.Plant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Deer extends Animal{


    public Deer(double weight, double maxSatiety) {
        super(weight, maxSatiety);
    }

    @Override
    public void eat(Location location) {
        if (!alive) {
            return;
        }
        Plant plant = location.removePlant();
        if (plant != null) {
            currentSatiety = Math.min(maxSatiety, currentSatiety + plant.getWeight());
            log.debug("Олень съел растение");
        }
    }

    @Override
    public void move(Island island, int currentX, int currentY) {
//todo zaglushka
    }

    @Override
    public void reproduce(Location location) {
//todo zaglushka
    }
}
