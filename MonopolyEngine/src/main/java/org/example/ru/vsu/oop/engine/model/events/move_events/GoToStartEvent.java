package org.example.ru.vsu.oop.engine.model.events.move_events;

import org.example.ru.vsu.oop.engine.api.cell.Cell;

public class GoToStartEvent extends MoveEvent {

    public GoToStartEvent(Cell cell) {
        super(cell, 0);
    }

    @Override
    public String getDescription() {
        return "Отправляйся в начало";
    }
}
