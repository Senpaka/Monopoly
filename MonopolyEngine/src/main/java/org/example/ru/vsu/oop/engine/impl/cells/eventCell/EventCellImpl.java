package org.example.ru.vsu.oop.engine.impl.cells.eventCell;

import org.example.ru.vsu.oop.engine.api.cell.SpecialCell;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public abstract class EventCellImpl implements SpecialCell {

    protected String name;
    protected String description;
    protected int position;

    public EventCellImpl(String name, String description, int position) {
        this.name = name;
        this.description = description;
        this.position = position;
    }

    @Override
    public abstract void applyEffect(Player player, GameEngine gameEngine);

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public abstract CellType getCellType();

    @Override
    public abstract void onLand(Player player, GameEngine gameEngine);

    @Override
    public abstract void onPass(Player player, GameEngine gameEngine);
}
