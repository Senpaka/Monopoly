package org.example.ru.vsu.oop.engine.impl.game;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.game.GameState;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.utils.DicePair;

public class GameEngineImpl implements GameEngine {

    GameState gameState;
    private boolean gameOver = false;
    DicePair dicePair;

    public GameEngineImpl(GameState gameState) {
        this.gameState = gameState;
        this.dicePair = new DicePair();
    }

    @Override
    public void startGame() {
        System.out.println("Игра началась");
        while (!gameOver){
            performTurn();
        }
        Player winner = getWinner();
        System.out.println("Победитель " + winner.getName());
    }

    @Override
    public void performTurn() {
        Player player = gameState.getCurrentPlayer();
        System.out.println("Ход игрока " + player.getName());

        int dice = dicePair.roll();
        System.out.println(player.getName() + " Бросает кубики и получает " + dice);

        movePlayer(player, dice);

        Cell cell = gameState.getBoard().getCell(player.getPosition());
        System.out.println(player.getName() + " Встал на клетку " + cell.getName());
        System.out.println("Она " + cell.getDescription());

        cell.onLand(player, this);
        cell.onPass(player, this);

        System.out.println("Баланс " + player.getBalance());

        gameOver = isGameOver();

        gameState.nextTurn();
    }

    @Override
    public void movePlayer(Player player, int steps) {
        int newPos = (player.getPosition() + steps) % gameState.getBoard().getSize();
        player.setPosition(newPos);
        System.out.println(player.getName() + "Перемещается на позицию" + newPos);
    }

    @Override
    public void processEvent() {
    }

    @Override
    public boolean isGameOver() {
        for (Player player: gameState.getPlayers()){
            if (player.getBalance() < 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public Player getWinner() {
        Player winner = null;
        int maxMoney = -1;
        for (Player player: gameState.getPlayers()){
            if (player.getBalance() > maxMoney){
                maxMoney = player.getBalance();
                winner = player;
            }
        }
        return winner;
    }
}
