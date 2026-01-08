package ru.vsu.oop.ru.vsu.oop.engine.model.events.chance;

import ru.vsu.oop.ru.vsu.oop.engine.api.event.RandomEvent;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;

import java.util.List;

public class PayEveryPlayerCard implements RandomEvent {

    private int amount;
    private String descriprion;

    public PayEveryPlayerCard(int amount, String descriprion) {
        this.amount = amount;
        this.descriprion = descriprion;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        List<Player> players = gameEngine.getPlayers();
        for(Player pl: players){
            if (!player.equals(pl) && player.spendMoney(this.amount)){
                pl.addMoney(this.amount);
            }
        }
    }

    @Override
    public String getDescription() {
        return this.descriprion;
    }

    @Override
    public CellType getType() {
        return CellType.CHANCE;
    }
}
