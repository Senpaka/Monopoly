package ru.vsu.oop.ru.vsu.oop.engine.model.events.communityChest;

import ru.vsu.oop.ru.vsu.oop.engine.api.event.RandomEvent;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class ReceiveMoneyCard implements RandomEvent {

    private int amount;
    private String description;

    public ReceiveMoneyCard(int amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        player.addMoney(amount);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public CellType getType() {
        return CellType.COMMUNITY;
    }
}
