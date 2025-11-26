package org.example.ru.vsu.oop.engine.impl.cells;

import org.example.ru.vsu.oop.engine.api.cell.SpecialCell;
import org.example.ru.vsu.oop.engine.api.event.Event;
import org.example.ru.vsu.oop.engine.api.event.StaticEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.enumObject.event.RandomEventType;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public class SpecialCellImpl implements SpecialCell {
    /*
    Класс для особых клеток с событиями
     */
    private String name;
    private String description;
    private int position;
    private CellType cellType = CellType.STATIC_EVENT;

    //Изменил на Event
    private Event event;

    public SpecialCellImpl(String name, String description, int position, Event event) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.event = event;
    }

    @Override
    public void applyEffect(Player player, GameEngine gameEngine) {
        /*
        Применения события
         */
        event.apply(player, gameEngine);
    }

    @Override
    public void onLand(Player player, GameEngine gameEngine) {
        /*
        Событие при вставании на клетку
         */
        applyEffect(player, gameEngine);
    }

    @Override
    public void onPass(Player player, GameEngine gameEngine) {

    }


    @Override
    public String getName() {
        /*
        Возвращает название события
         */
        return name;
    }

    @Override
    public String getDescription() {
        /*
        Возвращает описание события
         */
        return description;
    }

    @Override
    public CellType getCellType() {
        /*
        Возвращает тип клетки
         */
        return cellType;
    }



    @Override
    public int getPosition() {
        /*
        Возвращает позицию
         */
        return position;
    }

    @Override
    public void setPosition(int position) {
        /*
        Устанавливает позицию
         */
        this.position = position;
    }
}
