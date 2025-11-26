package org.example.ru.vsu.oop.engine.api.cell;

import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;

public interface Street extends Cell{
    /*
    Интерфейс для клеток с улицами
     */
    int getPrice();
    int getSellPrice();

    Player getOwner();
    void setOwner(Player player);

    ColorGroup getColorGroup();

    int getHouses();
    boolean hasHotel();
    void addHouse();
    void addHotel();

    int getHousePrice();
    int getHotelPrice();

    int getBaseRent();
    int getRentPrice();

    // потом сделать, заложение улиц
//    boolean isMortgaged();
//    int getMortgageValue();
//    void mortgage();
//    void unmortgage();

    void sellStreetToBank();
}
