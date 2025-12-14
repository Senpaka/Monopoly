package org.example.ru.vsu.oop.engine.api.cell;

import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;

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
