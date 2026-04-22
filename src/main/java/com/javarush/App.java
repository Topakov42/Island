package com.javarush;

import com.javarush.SimpleSimulation.MultithreadSimulation;
import com.javarush.config.SimulationConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    static void main(String[] args) {
// Смотреть аннотацию @Builder на классе SimulationConfig


        SimulationConfig config = new SimulationConfig();
        MultithreadSimulation multithreadSimulation = new MultithreadSimulation(config);
        multithreadSimulation.initialize();
        multithreadSimulation.start();

        log.info(" Начальное состояние : ");
        multithreadSimulation.printStatistics();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        multithreadSimulation.stop();


        log.info("Работа симуляция завершена  ");
    }
}

