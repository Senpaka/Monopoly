package org.example.ru.vsu.oop.engine.api.cell;

import org.example.ru.vsu.oop.engine.api.player.Player;

public interface SpecialCell extends Cell{
    SpecialEffect getEffect();

    void applyEffect(Player player);
}
