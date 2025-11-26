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
    public SpecialEffect getEffect() {
        return event.getEffect();
    }

    @Override
    public void applyEffect(Player player, GameEngine gameEngine) {
        event.apply(player, gameEngine);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public CellType getCellType() {
        return cellType;
    }



    @Override
    public void onLand(Player player, GameEngine gameEngine) {
        applyEffect(player, gameEngine);
    }

    @Override
    public void onPass(Player player, GameEngine gameEngine) {

    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }
}
