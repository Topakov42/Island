package com.javarush;

import com.javarush.SimpleSimulation.SimpleSimulation;
import com.javarush.config.SimulationConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    private static final int SIMPLE_SIMULATION_TICKS = 10;

    static void main(String[] args) {
// Смотреть аннотацию @Builder на классе SimulationConfig
        SimulationConfig config = SimulationConfig.builder()    //почему при попытке создать new Simulation config - idea ругается
                .islandWidth(5)  //размеры клетки острова (ширина)
                .islandHeight(5) // размеры клетки острова (высота)
                .initialWolf(2)  // Кол-во волков
                .initialRabbit(10) // Кол-во зайцев
                .initialDeer(5) // Кол-во оленей
                .plantsPerCell(1) // Кол-во расстений на 1 ячейку
                .build();


        //однопоточная симуляция

        SimpleSimulation simpleSimulation = new SimpleSimulation(config);
        simpleSimulation.initialize();

        //Выводим сконфигурированное состояние : error, info, debug
        log.info("Начально состояние симуляции");
        simpleSimulation.printStatistics();
        try {
            simpleSimulation.run(SIMPLE_SIMULATION_TICKS);
        } catch (InterruptedException e) {
            log.error("Ошибка при работе simpleSimulation");
            throw new RuntimeException(e);
        }


        log.info("Работа симуляция завершена  ");
    }
}

