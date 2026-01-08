package org.example.ru.vsu.oop.engine.model.events.chance;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

import java.util.List;

public class BirthdayComCard implements RandomEvent {

    private int money;
    private String  description;

    public BirthdayComCard(int money, String description) {
        this.money = money;
        this.description = description;
    }

    @Override
    public CellType getType() {
        return CellType.CHANCE;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        List<Player> players = gameEngine.getPlayers();
        for(Player pl: players){
            if (!player.equals(pl) && pl.spendMoney(this.money)){
                player.addMoney(this.money);
            }
        }
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
