package org.example.ru.vsu.oop.engine.api.game;

import org.example.ru.vsu.oop.engine.api.player.Player;

public interface GameEngine {
    /*
    Интерфейс движка
     */
    void startGame();
    void performTurn();
    void movePlayer(Player player, int steps);
    void processEvent();
    boolean isGameOver();
    Player getWinner();
}
