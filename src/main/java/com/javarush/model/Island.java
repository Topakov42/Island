package com.javarush.model;

import com.javarush.animal.Animal;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс остров - двумерный массив клеток (аналогия с играми с курса)
 * Используем индексацию: Сначало Y (строка), а потом X (столбец)
 *
 */

public class Island {
    @Getter
    private final int width;
    @Getter
    private final int height;
    private final Location[][] locations; // МАССИВ который инициализируется с размерами острова ( 10 *10 (размер поменяем)). В каждой ячейке у нас хранится список растений и животных.
    // списки добавляются в двойной массив в классе MultithreadSim.
    private final Map<Class <? extends Animal>, Integer> valueAnimalsIsland = new HashMap<>();




    public Island(int height, int width) {
        this.height = height;
        this.width = width;
        this.locations = new Location[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                locations[i][j] = new Location();
            }
        }
    }


    public Location getLocation(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Координаты выходят за размеры острова");
        }
        return locations[y][x];
    }
}
