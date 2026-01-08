package ru.vsu.oop.ru.vsu.oop.engine.api.event;

import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public interface StaticEvent extends Event{
    /*
    Интерфейс для ивентов статичных (события на клетках)
     */
    //Cell getSourceCell();
    SpecialEffect getEffect();
}
