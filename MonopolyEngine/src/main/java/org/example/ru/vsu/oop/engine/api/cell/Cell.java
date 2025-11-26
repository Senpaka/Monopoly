package org.example.ru.vsu.oop.engine.api.cell;

import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public interface Cell {
    String getName();
    String getDescription();

    CellType getCellType();

    void onLand(Player player, GameEngine gameEngine);
    void onPass(Player player, GameEngine gameEngine);

    int getPosition();
    void setPosition(int position);

}
