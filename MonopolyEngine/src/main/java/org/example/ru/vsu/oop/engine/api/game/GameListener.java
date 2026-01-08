package org.example.ru.vsu.oop.engine.api.game;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.cell.Property;
import org.example.ru.vsu.oop.engine.api.event.Event;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.player.Player;

import java.util.List;

public interface GameListener {

    void onGameStarted(List<Player> players);

    void onTurnEnded(Player player);

    void onDiceRolled(Player player, int value);

    void onPlayerMoved(Player player, int newPosition);

    void onCellPassed(Player player, Cell cell);

    void onCellLanded(Player player, Cell cell);

    void onBalanceChanged(Player player, int newBalance);

    void onMessage(String message);

    void onGameOver(Player winner);

    void onPropertyAvailable(Player player, Property property);

    void onCardDrawn(Player player, Event event);

    public void onRandomEvent(Player player, RandomEvent event);

}

