package org.example.ru.vsu.oop.engine.api.event;

import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public interface Event {
    void apply(Player player, GameEngine gameEngine);
    String getDescription();

}
