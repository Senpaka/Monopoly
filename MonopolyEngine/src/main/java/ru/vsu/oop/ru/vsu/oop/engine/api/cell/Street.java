package ru.vsu.oop.ru.vsu.oop.engine.api.cell;

import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;

public interface Street extends Property{
    /*
    Интерфейс для клеток с улицами
     */

    ColorGroup getColorGroup();

    int getHouses();
    boolean hasHotel();
    void addHouse();
    void addHotel();

    int getHousePrice();
    int getHotelPrice();

    int getBaseRent();
}
