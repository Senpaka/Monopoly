package org.example.ru.vsu.oop.engine.model.events;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.event.StaticEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.event.RandomEventType;

public class ReceiveMoneyEvent implements StaticEvent {
    private final int amount;
    private final Cell cell;

    public ReceiveMoneyEvent(int amount, Cell cell) {
        this.amount = amount;
        this.cell = cell;
    }

    @Override
    public void apply(Player player, GameEngine engine) {
        player.addMoney(this.amount);
    }

    @Override
    public String getDescription() {
        return "Получи" + amount + "денег";
    }

    @Override
    public Cell getSourceCell() {
        return cell;
    }
}
