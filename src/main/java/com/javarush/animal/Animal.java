package com.javarush.animal;


import java.util.Map;
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
    protected  double speed = 1; // скорость перемещения
    protected double maxCountPerCell; // максмальное количество животных этого ввида


    // Карта вероятности поедания других животных

    protected Map < Class  <? extends Animal> , Integer> eatingProbabilities;

    public Animal(double weight, double maxSatiety) {
        this.weight = weight;
        this.maxSatiety = maxSatiety;
        this.currentSatiety = maxSatiety;
    }

    // eat , move , reproduce


    public abstract void eat (Location location);

    public abstract void move(Island island, int currentX, int currentY);

    public abstract void reproduce(Location location);

    public void die () {
        this.alive = false;
    }



}
