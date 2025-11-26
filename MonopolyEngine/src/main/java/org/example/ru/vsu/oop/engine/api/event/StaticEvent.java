package org.example.ru.vsu.oop.engine.api.event;

import org.example.ru.vsu.oop.engine.api.cell.Cell;

public interface StaticEvent extends Event{
    Cell getSourceCell();
}
