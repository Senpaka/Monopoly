package org.example.ru.vsu.oop.engine.model.events.move_events;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.event.StaticEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public abstract class MoveEvent implements StaticEvent {
    protected Cell cell;
    protected int targetPosition;

    protected SpecialEffect specialEffect;

    public MoveEvent(Cell cell, SpecialEffect specialEffect, int targetPosition) {
        this.cell = cell;
        this.targetPosition = targetPosition;
        this.specialEffect = specialEffect;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine){
        player.setPosition(this.targetPosition);
    }

//    @Override
//    public Cell getSourceCell() {
//        return this.cell;
//    }
    @Override
    public SpecialEffect getEffect(){
        return this.specialEffect;
    }
}
