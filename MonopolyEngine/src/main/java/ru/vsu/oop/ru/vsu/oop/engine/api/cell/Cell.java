package ru.vsu.oop.ru.vsu.oop.engine.api.cell;

import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;

public interface Cell {
    /*
    Интерфейс клетки
     */
    String getName();
    String getDescription();

    CellType getCellType();

    void onLand(Player player, GameEngine gameEngine);
    void onPass(Player player, GameEngine gameEngine);

    int getPosition();
    void setPosition(int position);

}
