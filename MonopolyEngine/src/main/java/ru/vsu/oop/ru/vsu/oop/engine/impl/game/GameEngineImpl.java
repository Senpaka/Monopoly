package ru.vsu.oop.ru.vsu.oop.engine.impl.game;

import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Cell;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Property;
import ru.vsu.oop.ru.vsu.oop.engine.api.event.RandomEvent;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameListener;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameState;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.impl.board.Board;
import ru.vsu.oop.ru.vsu.oop.engine.utils.DicePair;

import java.util.List;

import static java.rmi.server.LogStream.log;

public class GameEngineImpl implements GameEngine {
    /*
    Класс игрового движка
     */
    GameState gameState;
    private boolean gameOver = false;
    private GameListener listener;
    private int movesLeft = 0;
    DicePair dicePair;

    public GameEngineImpl(GameState gameState) {
        this.gameState = gameState;
        this.dicePair = new DicePair();
    }

    public void setListener(GameListener listener){
        this.listener = listener;
    }

    @Override
    public void startGame() {
        /*
        Начало игры
         */
        System.out.println("Игра началась");
        while (!gameOver){
            performTurn();
        }
        Player winner = getWinner();
        System.out.println("Победитель " + winner.getName());
    }

    @Override
    public void performTurn() {
        /*
        Выполнение хода
         */
        Player player = gameState.getCurrentPlayer();

        int dice = dicePair.roll();

        System.out.println(player.getName() + " Бросает кубики и получает " + dice);

        for (int i = 0; i < dice; i++) {
            movePlayer(player, 1);
            Cell cell = gameState.getBoard().getCell(player.getPosition());
            cell.onPass(player, this);
        }

        Cell cell = gameState.getBoard().getCell(player.getPosition());
        System.out.println(player.getName() + " Встал на клетку " + cell.getName());
        System.out.println("Она " + cell.getDescription());
        cell.onLand(player, this);

        System.out.println("Баланс " + player.getBalance());

        gameOver = isGameOver();

        gameState.nextTurn();
    }

    public int rollDice(){
        movesLeft = dicePair.roll();
        listener.onDiceRolled(gameState.getCurrentPlayer(), movesLeft);
        return movesLeft;
    }

    public void moveStep(){
        if (movesLeft <= 0){
            return;
        }

        Player player = gameState.getCurrentPlayer();
        int oldPos = player.getPosition();

        movePlayer(player, 1);
        movesLeft--;

        listener.onPlayerMoved(player, player.getPosition());

        Cell cell = getBoard().getCell(player.getPosition());
        cell.onPass(player, this);
    }

    public boolean hasMovesSteps(){
        return movesLeft > 0;
    }

    public int getMovesLeft(){
        return movesLeft;
    }

    public void land() {
        Player player = gameState.getCurrentPlayer();
        Cell cell = getBoard().getCell(player.getPosition());

        listener.onMessage(player.getName() + " встал на " + cell.getName());
        listener.onCellLanded(player, cell);

        // Логика cell
        cell.onLand(player, this);

        // Если клетка свободна и это Property — UI должен показать окно покупки
//        if (cell instanceof Property property && !property.hasOwner()) {
//            listener.onPropertyAvailable(player, property);
//        }
    }

    @Override
    public void onCellLanded(Player player, Cell cell) {
        if (cell instanceof Property property) {
            if (!property.hasOwner()) {
                // Движок сообщает, что есть доступная покупка
                listener.onPropertyAvailable(player, property);
            } else if (property.getOwner() != player) {
                log(player.getName() + " платит аренду " + property.getOwner().getName());
            }
        }
    }


    public void endTurn(){
        gameOver = isGameOver();
        gameState.nextTurn();
        listener.onTurnEnded(gameState.getCurrentPlayer());
    }

    @Override
    public void movePlayer(Player player, int steps) {
        /*
        передвижение игрока
         */
        int newPos = (player.getPosition() + steps) % gameState.getBoard().getSize();
        player.setPosition(newPos);
    }

    @Override
    public void processEvent() {
    }

    @Override
    public void sendPlayerTo(Player player, int position) {
        player.setPosition(position);
    }

    @Override
    public void addMessage(String massage) {
        System.out.println(massage);
    }

    @Override
    public boolean isGameOver() {
        /*
        проверка на проигрыш
         */
        for (Player player: gameState.getPlayers()){
            if (player.getBalance() < 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public Player getWinner() {
        /*
        получение победителя
         */
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

    public Player getCurrentPlayer(){
        return this.gameState.getCurrentPlayer();
    }

    @Override
    public List<Player> getPlayers(){
        return this.gameState.getPlayers();
    }

    @Override
    public RandomEvent drawChance(){
        return gameState.getChance().drawCard();
    }

    @Override
    public RandomEvent drawCommunity(){
        return gameState.getCommunity().drawCard();
    }

    @Override
    public Board getBoard() {return gameState.getBoard();}
}
