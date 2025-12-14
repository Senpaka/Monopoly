package org.example.ru.vsu.oop.engine.model.events.staticEvents;

import org.example.ru.vsu.oop.engine.api.event.StaticEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public class PayMoneyEvent implements StaticEvent {
    /*
    Реализация ивента траты денег в казну
     */
    private final int amount;

    public PayMoneyEvent(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        player.spendMoney(this.amount);
    }

    @Override
    public String getDescription() {
        return "Заплати в казну " + this.amount + "денег";
    }

    @Override
    public SpecialEffect getEffect() {
        return SpecialEffect.PAY_MONEY;
    }
}
