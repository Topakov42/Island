package com.javarush.animal;


import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.javarush.model.Location;
import com.javarush.model.Island;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j

public abstract class Animal {
    protected double weight;  // вес
    protected double maxSatiety; // сытость максимальная
    protected double currentSatiety; // текущая сытость
    protected boolean alive = true;  // статус жизни животного
    protected double speed = 1; // скорость перемещения
    protected double maxCountPerCell; // максмальное количество животных этого ввида
    protected volatile Location currentLocation; // текущее местонахождение животного


    // Карта вероятности поедания других животных

    protected Map<Class<? extends Animal>, Integer> eatingProbabilities;

    public Animal(double weight, double maxSatiety) {
        this.weight = weight;
        this.maxSatiety = maxSatiety;
        this.currentSatiety = maxSatiety;
    }

    // eat , move , reproduce


    public abstract void eat(Location location);

    public void move(Island island, int currentX, int currentY) {
        if (!alive) {
            return;
        }
        if (currentLocation == null) {
            log.warn("Животное {} не имеет текущей локации. Перемещение не возможно", this);
            return;
        }
        int dirction = ThreadLocalRandom.current().nextInt(4);
        int newX = currentX;
        int newY = currentY;

        switch (dirction) {
            case 0: newY = Math.max(0, currentY - 1); break;  // вверх Y
            case 1: newX = Math.min(island.getWidth() - 1, currentX + 1 ); break; // вправо X
            case 2: newY = Math.min(island.getHeight() - 1, currentY + 1 ); break; // вниз Y
            case 3: newX = Math.max(0, currentX - 1); break; // влево X

        }
    }

    ;

    public abstract void reproduce(Location location);

    public void die() {
        this.alive = false;
    }


}
