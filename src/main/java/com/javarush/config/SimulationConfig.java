package com.javarush.config;

import lombok.Builder;
import lombok.Data;

/**
 * Конфигурация приложения
 */


@Data
@Builder //  Протиать про Lombok( Паттерн Билдер )
public class SimulationConfig {
    //размеры острова
    private int islandWidth;
    private int islandHeight;
    // Популяции
    private int initialWolf;
    private int initialRabbit;
    private int initialDeer;
    private int plantsPerCell; // кол-во растение которые добавляются за 1 такт в каждую клетку.
    // Дюрация в мс.
    private long ticketDurationMs;




}
