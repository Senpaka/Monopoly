package org.example.ru.vsu.oop.engine.api.event;

import org.example.ru.vsu.oop.engine.model.enumObject.event.RandomEventType;

public interface RandomEvent extends Event{
    RandomEventType getType();
}
