package org.example.ru.vsu.oop.engine.impl.game;

import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;

public class GameEngineImpl implements GameEngine {
    @Override
    public void startGame() {

    }

    @Override
    public void performTurn() {

    }

    @Override
    public void rollDice() {

    }

    @Override
    public void movePlayer(Player player, int steps) {
        player.move(steps);
    }

    @Override
    public void processEvent() {

    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public Player getWinner() {
        return null;
    }
}
