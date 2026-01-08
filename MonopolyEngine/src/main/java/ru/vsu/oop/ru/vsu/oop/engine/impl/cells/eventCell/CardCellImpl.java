package ru.vsu.oop.ru.vsu.oop.engine.impl.cells.eventCell;

import ru.vsu.oop.ru.vsu.oop.engine.api.event.Event;
import ru.vsu.oop.ru.vsu.oop.engine.api.event.RandomEvent;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class CardCellImpl extends EventCellImpl{

    private final CellType cellType;
    private RandomEvent event;

    public CardCellImpl(String name, String description, int position, CellType cellType) {
        super(name, description, position);
        this.cellType = cellType;
        this.event = null;
    }

    public void applyEffect(Player player, GameEngine gameEngine) {
        event.apply(player, gameEngine);
    }

    @Override
    public CellType getCellType() {
        return this.cellType;
    }

    @Override
    public void onLand(Player player, GameEngine gameEngine) {
        Event event = gameEngine.drawRandomEvent(cellType);

        if (event == null) {
            throw new IllegalStateException("Колода пуста");
        }

        gameEngine.onCardDrawn(player, event);
    }

    @Override
    public void onPass(Player player, GameEngine gameEngine) {

    }
}
