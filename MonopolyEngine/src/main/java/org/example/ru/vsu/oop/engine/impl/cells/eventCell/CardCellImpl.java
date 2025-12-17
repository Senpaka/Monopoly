package org.example.ru.vsu.oop.engine.impl.cells.eventCell;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

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
        if (cellType == CellType.COMMUNITY){
            this.event = gameEngine.drawCommunity();
            gameEngine.addMessage("Вытянута карта общественная казна");
        }else{
            this.event = gameEngine.drawChance();
            gameEngine.addMessage("Вытянута карта шанс");
        }

        if (event != null){
            applyEffect(player, gameEngine);
            this.event = null;
        }else{
            throw new NullPointerException("Колода пуста, либо содержит Null значения");
        }
    }

    @Override
    public void onPass(Player player, GameEngine gameEngine) {

    }
}
