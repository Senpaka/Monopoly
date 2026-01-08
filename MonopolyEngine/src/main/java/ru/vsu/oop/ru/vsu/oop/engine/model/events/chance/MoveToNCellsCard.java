package ru.vsu.oop.ru.vsu.oop.engine.model.events.chance;

import ru.vsu.oop.ru.vsu.oop.engine.api.event.RandomEvent;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class MoveToNCellsCard implements RandomEvent {

    private int steps;
    private String description;

    public MoveToNCellsCard(int steps, String description) {
        this.steps = steps;
        this.description = description;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        gameEngine.movePlayer(player, steps);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public CellType getType() {
        return CellType.CHANCE;
    }
}
