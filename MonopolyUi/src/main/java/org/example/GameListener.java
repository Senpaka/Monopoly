package org.example;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.player.Player;

import java.util.List;

public interface GameListener {

    void onGameStarted(List<Player> players);

    void onTurnStarted(Player player);

    void onDiceRolled(Player player, int value);

    void onPlayerMoved(Player player, int newPosition);

    void onCellPassed(Player player, Cell cell);

    void onCellLanded(Player player, Cell cell);

    void onBalanceChanged(Player player, int newBalance);

    void onMessage(String message);

    void onGameOver(Player winner);
}

