package org.example.ru.vsu.oop.engine.impl.cells.property;

import org.example.ru.vsu.oop.engine.api.cell.Property;
import org.example.ru.vsu.oop.engine.api.cell.Utilities;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

import java.util.List;

public class UtilitiesImpl extends PropertyImpl implements Utilities {
    private final int ONE_UTILITIES_MULTIPLY = 4;
    private final int TWO_UTILITIES_MULTIPLY = 10;

    public UtilitiesImpl(String name, String description, int position, int price) {
        super(name, description, position, price);
    }

    @Override
    public int getRentPrice() {
        throw new IllegalArgumentException("Не указано кол-во шагов!");
    }

    @Override
    public int getRentPrice(int steps) {

        if (super.owner.getCountPropertyType(this.getCellType()) == 1){
            return steps * ONE_UTILITIES_MULTIPLY;
        }

        return steps * TWO_UTILITIES_MULTIPLY;
    }

    @Override
    public int getMultiply(Player player) {
        List<Property> properties = player.getProperty();
        int cnt = 0;

        for(Property property: properties){
            if (property instanceof Utilities){
                cnt += 1;
            }
        }

        return cnt == 2 ? TWO_UTILITIES_MULTIPLY : ONE_UTILITIES_MULTIPLY;
    }

    @Override
    public void sellToBank() {
        super.owner.addMoney(getSellPrice());
        super.owner = null;
    }

    @Override
    public CellType getCellType() {
        return CellType.UTILITIES;
    }

    @Override
    public void onPass(Player player, GameEngine gameEngine) {

    }
}
