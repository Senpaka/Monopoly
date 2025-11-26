package org.example.ru.vsu.oop.engine.api.event;

import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.impl.Player;

public interface Event {
    void apply(Player player, GameEngine engine);
    String getDescription();
}
