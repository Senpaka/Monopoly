package org.example.ru.vsu.oop.engine.model.events.move_events;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public class GoToStartEvent extends MoveEvent {

    public GoToStartEvent(Cell cell) {
        super(cell, SpecialEffect.GO_TO_START, 0);
    }

    @Override
    public String getDescription() {
        return "Отправляйся в начало";
    }
}
