package ru.vsu.oop.ru.vsu.oop.engine.api.cell;

import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;

public interface SpecialCell extends Cell{
    /*
    Интерфейс для клеток с событиями
     */
    void applyEffect(Player player, GameEngine gameEngine);
}
