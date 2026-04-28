package com.javarush.model;

import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

@Data
public class Plant {
    private double weight = 1.0;


    public static int plantGrowthRate() {
        int rate = ThreadLocalRandom.current().nextInt( 8);
        return rate;
    }


}
