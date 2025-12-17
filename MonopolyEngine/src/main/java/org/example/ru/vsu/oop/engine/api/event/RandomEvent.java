package org.example.ru.vsu.oop.engine.api.event;

import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public interface RandomEvent extends Event{
    /*
    Интерфейс для случайных ивентов (карточки)
     */
    CellType getType();
}
