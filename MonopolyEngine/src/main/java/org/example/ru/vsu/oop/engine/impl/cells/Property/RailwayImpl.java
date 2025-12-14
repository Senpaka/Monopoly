package org.example.ru.vsu.oop.engine.impl.cells.Property;

import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class RailwayImpl extends PropertyImpl {

    public RailwayImpl(String name, String description, int position, int price, int baseRent) {
        super(name, description, position, price, baseRent);
    }

    @Override
    public int getRentPrice() {
        /*
        Возвращает пересчитаннцю ренту
         */

        return super.baseRent * super.owner.getCountPropertyType(this.getCellType());
    }

    @Override
    public void sellToBank() {
        /*
        Продажа улицы в банк
         */
        super.owner.addMoney(getSellPrice());
        super.owner = null;
    }

    @Override
    public CellType getCellType() {
        /*
        Возвращает тип клетки
         */
        return CellType.RAILWAY;
    }

    @Override
    public void onPass(Player player, GameEngine gameEngine) {

    }
}
