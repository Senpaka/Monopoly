package org.example.ru.vsu.oop.engine.impl.cells.property;

import org.example.ru.vsu.oop.engine.api.cell.Railway;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class RailwayImpl extends PropertyImpl implements Railway {

    private final int baseRent;

    public RailwayImpl(String name, String description, int position, int price, int baseRent) {
        super(name, description, position, price);
        this.baseRent = baseRent;
    }

    @Override
    public int getRentPrice() {
        /*
        Возвращает пересчитаннцю ренту
         */

        return this.baseRent * super.owner.getCountPropertyType(this.getCellType());
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

    @Override
    public int getBaseRent() {
        return this.baseRent;
    }
}
