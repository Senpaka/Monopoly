package org.example.ru.vsu.oop.engine.model.events.communityChest;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

import java.util.ArrayList;
import java.util.List;

public class BirthdayComCard implements RandomEvent {

    int money;

    public BirthdayComCard(int money) {
        this.money = money;
    }

    @Override
    public CellType getType() {
        return CellType.CHANCE;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        List<Player> players = gameEngine.getPlayers();
        for(Player pl: players){
            if (!players.equals(pl)){
                pl.spendMoney(this.money);
                player.addMoney(this.money);
            }
        }
        gameEngine.addMessage("Все игроки подарили игроку: " + player.getName() + " по " + this.money);
    }

    @Override
    public String getDescription() {
        return "У вас день рождения получите " + this.money + " денег от каждого игрока";
    }
}
