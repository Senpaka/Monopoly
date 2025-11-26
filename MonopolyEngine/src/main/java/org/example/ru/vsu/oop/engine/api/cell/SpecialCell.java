package org.example.ru.vsu.oop.engine.api.cell;

import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public interface SpecialCell extends Cell{
    SpecialEffect getEffect();

    void applyEffect(Player player, GameEngine gameEngine);
}
