package com.javarush.model;

import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

/**
 * растение - простой объект. не может двигаться. Имеет только вес. Мб добавим фичу для роста.
 * Так же у нас есть поле (массив 100 *20). реализуем штуку чтобы травоядные попав в клетку без расстения, сразу же бежали.
 * Так же, если в клетке имеется растение, шанс на то чтобы быть съеденным уменьшается.
 */

@Data
public class Plant {
private double weight = 1.0;



public static int plantGrowthRate() {
    int rate = ThreadLocalRandom.current().nextInt(8);
    return rate;
}





}
