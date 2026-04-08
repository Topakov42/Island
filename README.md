#island 

com.javarush.island
herbivorous
predator

1.  Пакеты: 
1) animal - Пакет с классами животных
 - Animal - абстрактный класс 
 -  - Wolf ( predator) \ Волк 
 - - Boa ( predator )  \ Удав 
 - - Fox (predator) \ Лиса
 - - Bear (predator)  \ Медведь
 - - Deer (herbivorous)  \ Олень
 - - Rabbit (herbivorous) \ Кролик 
 - - Horse (herbivorous) \ Лошадь 
 - - Goat (herbivorous)  \ Коза
 - - Sheep ( herbivorous) \ Овца 
 - - Caterpillar (Caterpillar) \ Гусеница
 - - Plants (Caterpillar) \ Растение 
2) config - Конфигурация всего приложения 
SimulationConfig - конфигурация для многопоточки и однопоточки 

3) model - Модельные классы 
- Island - Остров .  клетки на острове (100*20) [100][20]
- Location - клетки на острове. содержит растения\животных 
- Plant - растение 
4) simulation - Логика симуляция ()
SimpleSimulation 

AppCore - точка входа

2 Подключение зависимостей в pom.xml


