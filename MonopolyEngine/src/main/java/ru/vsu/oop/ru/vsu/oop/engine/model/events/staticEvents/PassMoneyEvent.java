package ru.vsu.oop.ru.vsu.oop.engine.model.events.staticEvents;

import ru.vsu.oop.ru.vsu.oop.engine.api.event.StaticEvent;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public class PassMoneyEvent implements StaticEvent {
    private final int amount;

    public PassMoneyEvent(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        player.addMoney(this.amount);
        //gameEngine.addMessage(player.getName() + " получает $" + this.amount);
    }

    @Override
    public String getDescription() {
        return "Получи " + this.amount + "$";
    }

    @Override
    public SpecialEffect getEffect() {
        return SpecialEffect.RECEIVE_MONEY;
    }

    @Override
    public CellType getType() {
        return CellType.SPECIAL_CELL;
    }
}
