package org.example.ru.vsu.oop.engine.api;

public interface Player {
    String getName();

    void setPosition(int steps);
    int getPosition();

    void getMoney(int money);
    void takeMoney(int cost);
    int getBalance();
}
