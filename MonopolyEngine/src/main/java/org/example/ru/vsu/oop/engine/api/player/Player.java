package org.example.ru.vsu.oop.engine.api.player;

import org.example.ru.vsu.oop.engine.api.cell.Property;
import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

import java.util.List;

public interface Player {
    /*
    Интерфейс игрока
     */
    String getName();

    List<Property> getProperty();
    void addProperty(Property street);

    void move(int steps);
    void setPosition(int position);
    int getPosition();

    void addMoney(int amount);
    boolean spendMoney(int amount);
    int getBalance();

    int getCountPropertyType(CellType cellType);
}
