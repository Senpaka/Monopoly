package org.example.ru.vsu.oop.engine.api.game;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;

import java.util.List;

public interface GameEngine {
    /*
    Интерфейс движка
     */
    void startGame();
    void performTurn();
    void movePlayer(Player player, int steps);
    void processEvent();
    void sendPlayerTo(Player player, int position);
    void addMessage(String massage);
    boolean isGameOver();
    Player getWinner();
    List<Player> getPlayers();
    RandomEvent drawChance();
    RandomEvent drawCommunity();
    Board getBoard();
}
