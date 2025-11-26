package org.example.ru.vsu.oop.engine.api.event;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public interface StaticEvent extends Event{
    Cell getSourceCell();
    SpecialEffect getEffect();
}
