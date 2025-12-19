package org.example.ru.vsu.oop.engine.model.events.chance;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class GoToNCellCard implements RandomEvent {

    private int position;
    private String description;

    public GoToNCellCard(int position, String description) {
        this.position = position;
        this.description = description;
    }

    @Override
    public CellType getType() {
        return CellType.CHANCE;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        player.setPosition(position);
        gameEngine.addMessage(this.description);
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
