package org.example.ru.vsu.oop.engine.model.events.staticEvents;

import org.example.ru.vsu.oop.engine.api.event.StaticEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

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
}
