package org.example.ru.vsu.oop.engine.api.cell;

import org.example.ru.vsu.oop.engine.api.player.Player;

public interface Property extends Cell{

    int getPrice();
    int getSellPrice();

    Player getOwner();
    void setOwner(Player player);
    boolean hasOwner();

    int getRentPrice();

    void sellToBank();

    // потом сделать, заложение улиц
//    boolean isMortgaged();
//    int getMortgageValue();
//    void mortgage();
//    void unmortgage();
}
