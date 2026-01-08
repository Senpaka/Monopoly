package ru.vsu.oop.ru.vsu.oop.engine.model.events.staticEvents;

import ru.vsu.oop.ru.vsu.oop.engine.api.event.StaticEvent;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

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

    @Override
    public CellType getType() {
        return CellType.SPECIAL_CELL;
    }
}
