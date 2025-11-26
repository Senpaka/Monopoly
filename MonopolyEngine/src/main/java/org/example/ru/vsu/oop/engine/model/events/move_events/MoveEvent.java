package org.example.ru.vsu.oop.engine.model.events.move_events;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.event.StaticEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;

public abstract class MoveEvent implements StaticEvent {
    protected Cell cell;
    protected int targetPosition;

    public MoveEvent(Cell cell, int targetPosition) {
        this.cell = cell;
        this.targetPosition = targetPosition;
    }

    @Override
    public void apply(Player player, GameEngine engine){
        player.setPosition(this.targetPosition);
    }

    @Override
    public Cell getSourceCell() {
        return this.cell;
    }
}
