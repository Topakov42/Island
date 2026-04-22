package com.javarush.animal;

import com.javarush.config.SimulationConfig;
import com.javarush.model.Island;
import com.javarush.model.Location;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Wolf extends Animal {

    private static final double WEIGHT = 50;
    private static final double MAX_COUNT_PER_CELL = 30;
    private static final double MAX_SATIETY = 8;


    public Wolf() {
        super(WEIGHT, MAX_SATIETY );
    }

    @Override
    public void eat(Location location, SimulationConfig config) {
// жив ли объект?
        if (!alive) {
            return;
        }

        for (Animal prey : location.getAnimals()) {
            if (prey == this || !prey.isAlive()) continue;              // у живого - isAlive() - тру.  !prey.isAlive()) - проверяем что животное погибло. проверка что за животное
            Integer probability = config.getMapEating().get(Wolf.class).get(prey.getClass());   // заглядываем в мапу чтобы получить вероятность -  если это кролик - процент его съесть - 1 %
            if (probability != null && ThreadLocalRandom.current().nextInt(100) < probability) {  // если вероятность не равна 0 или вероятность быть съеденым больше рандома
                location.removeAnimal(prey);   // удаляем животное
                prey.die();  // ставим статус животному - умер
                currentSatiety = Math.min(maxSatiety, currentSatiety + prey.getWeight());  // текущая сытость - берем мин значение ( максимальная сытость, вес жертвы)
                log.info("Волк съел {} ", prey.getClass().getSimpleName());
                break;
            }
        }
    }

    @Override
    public void move(Island island, int currentX, int currentY) {
//ЗАГЛУШКА
    }

    @Override
    public void reproduce(Location location) {
// ЗАГЛУШКА
    }
}
