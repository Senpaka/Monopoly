package org.example.ru.vsu.oop.engine.api.player;

import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

import java.util.List;

public interface Player {
    /*
    Интерфейс игрока
     */
    String getName();

    List<Street> getStreets();
    void addStreet(Street street);

    void move(int steps);
    void setPosition(int position);
    int getPosition();

    void addMoney(int amount);
    int spendMoney(int amount);
    int getBalance();

    int getCountPropertyType(CellType cellType);
}
