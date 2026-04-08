package com.javarush.animal;


import java.util.Map;

public abstract class Animal {
    protected double weight;  // вес
    protected double maxSatiety; // сытость максимальная
    protected double currentSatiety; // текущая сытость
    protected boolean alive = true;  // статус жизни животного
    protected int speed = 1; // скорость перемещения


    // Карта вероятности поедания других животных

    protected Map < Class  <? extends Animal> , Integer> eatingProbabilities;

    public Animal(double weight, double maxSatiety, double currentSatiety) {
        this.weight = weight;
        this.maxSatiety = maxSatiety;
        this.currentSatiety = currentSatiety;
    }


    // eat , move , reproduce


    public abstract void eat (Location location);

    public abstract void move(Island island, int currentX, int currentY);

    public abstract void reproduce(Location location);

    public void die () {
        this.alive = false;
    }



}
