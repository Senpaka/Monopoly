package ru.vsu.oop.ru.vsu.oop.engine.api.event;

import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;

public interface Event {
    /*
    Интерфейс ивентов
     */
    void apply(Player player, GameEngine gameEngine);
    String getDescription();
    CellType getType();
}
