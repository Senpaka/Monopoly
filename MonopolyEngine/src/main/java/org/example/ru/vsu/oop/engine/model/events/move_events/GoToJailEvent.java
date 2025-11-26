package org.example.ru.vsu.oop.engine.model.events.move_events;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public class GoToJailEvent extends MoveEvent {

    public GoToJailEvent(Cell cell) {
        super(cell, SpecialEffect.GO_TO_JAIL, 10);
    }

    @Override
    public String getDescription() {
        return "Отправляйся в тюрьму";
    }
}
