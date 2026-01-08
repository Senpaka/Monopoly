package ru.vsu.oop.ru.vsu.oop.engine.impl.cells.eventCell;

import ru.vsu.oop.ru.vsu.oop.engine.api.event.Event;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class SpecialCellImpl extends EventCellImpl{
    /*
    Класс для особых клеток с событиями
     */

    protected Event event;

    public SpecialCellImpl(String name, String description, int position, Event event) {
        super(name, description, position);
        this.event = event;
    }

    public void applyEffect(Player player, GameEngine gameEngine) {
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
    public CellType getCellType() {
        /*
        Возвращает тип клетки
         */
        return CellType.SPECIAL_CELL;
    }

}
