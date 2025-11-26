package org.example.ru.vsu.oop.engine.api.cell;

import org.example.ru.vsu.oop.engine.api.player.Player;

public interface Cell {
    String getName();
    String getDescription();

    CellType getCellType();

    void onLand(Player player);
    void onPass(Player player);

    int getPosition();
    void setPosition(int position);
}
