package com.javarush;

import com.javarush.SimpleSimulation.MultithreadSimulation;
import com.javarush.SimpleSimulation.SimpleSimulation;
import com.javarush.config.SimulationConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    private static final int SIMPLE_SIMULATION_TICKS = 10;

    static void main(String[] args) {
// Смотреть аннотацию @Builder на классе SimulationConfig
        SimulationConfig config = SimulationConfig.builder()    //почему при попытке создать new Simulation config - idea ругается
                .islandWidth(10)  //размеры клетки острова (ширина)
                .islandHeight(10) // размеры клетки острова (высота)
                .initialWolf(5)  // Кол-во волков
                .initialRabbit(50) // Кол-во зайцев
                .initialDeer(20) // Кол-во оленей
                .plantsPerCell(3) // Кол-во расстений на 1 ячейку
                .ticketDurationMs(2000)
                .build();


        //однопоточная симуляция
//
//        SimpleSimulation simpleSimulation = new SimpleSimulation(config);
//        simpleSimulation.initialize();

        //Выводим сконфигурированное состояние : error, info, debug
//        log.info("Начально состояние симуляции");
//        simpleSimulation.printStatistics();
//        try {
//            simpleSimulation.run(SIMPLE_SIMULATION_TICKS);
//        } catch (InterruptedException e) {
//            log.error("Ошибка при работе simpleSimulation");
//            throw new RuntimeException(e);
//        }
        MultithreadSimulation multithreadSimulation = new MultithreadSimulation(config);
        multithreadSimulation.initialize();
        multithreadSimulation.start();


        log.info(" Начальное состояние : ");
        multithreadSimulation.printStatistics();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        multithreadSimulation.stop();


        log.info("Работа симуляция завершена  ");
    }
}

