package org.example.ru.vsu.oop.engine.impl.cells;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.enumObject.event.RandomEventType;

public class RandomEventImpl implements RandomEvent {

    private String name;
    private String description;
    private int position;
    private CellType cellType = CellType.RANDOM_EVENT;

    private RandomEventType eventType;
    private RandomEvent event;

    public RandomEventImpl(String name, String description, int position, RandomEventType eventType, RandomEvent event) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.eventType = eventType;
        this.event = event;
    }

    @Override
    public RandomEventType getType() {
        return eventType;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        event.apply(player, gameEngine);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public RandomEventType getEventType() {
        return eventType;
    }

    public RandomEvent getEvent() {
        return event;
    }
}
