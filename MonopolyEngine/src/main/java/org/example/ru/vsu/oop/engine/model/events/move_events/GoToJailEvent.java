package org.example.ru.vsu.oop.engine.model.events.move_events;

import org.example.ru.vsu.oop.engine.api.cell.Cell;

public class GoToJailEvent extends MoveEvent {

    public GoToJailEvent(Cell cell) {
        super(cell, 10);
    }

    @Override
    public String getDescription() {
        return "Отправляйся в тюрьму";
    }
}
