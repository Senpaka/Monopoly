package org.example.ru.vsu.oop.engine.api.player;

import org.example.ru.vsu.oop.engine.api.cell.Street;

import java.util.List;

public interface Player {
    String getName();

    List<Street> getStreets();

    void move(int steps);
    void setPosition(int position);
    int getPosition();

    void addMoney(int amount);
    boolean spendMoney(int amount);
    int getBalance();
}
