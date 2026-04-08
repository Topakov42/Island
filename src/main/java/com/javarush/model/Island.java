package com.javarush.model;

import lombok.Getter;

/**
 * Класс остров - двумерный массив клеток (аналогия с играми с курса)
 * Используем индексацию: Сначало Y (строка) , а потом X (столбец)
 *
 */

public class Island {
    @Getter
    private final int widht;

    @Getter
    private final int height;



    private final Location[][] locations;


    public Island(int height, int widht) {
        this.height = height;
        this.widht = widht;
        this.locations = new Location[height][widht];
        for (int i = 0; i <height ; i++) {
            for (int j = 0; j <widht ; j++) {
                locations[i][j] = new Location();
            }
        }
    }


    public Location getLocation(int x, int y){
if (x < 0 || x >= widht || y < 0 || y >=height) {
    throw  new IllegalAccessException("Координаты выходят за размеры острова");
}

return locations [y][x];
    }


}
