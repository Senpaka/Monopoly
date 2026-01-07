package ru.vsu.oop.ru.vsu.oop.engine.api.game;

import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Cell;
import ru.vsu.oop.ru.vsu.oop.engine.api.event.RandomEvent;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.impl.board.Board;

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
    public void endTurn();
    public void land();
    public boolean hasMovesSteps();
    public void moveStep();
    public int rollDice();
    public void setListener(GameListener listener);
    public int getMovesLeft();
    public void onCellLanded(Player player, Cell cell);

        Player getCurrentPlayer();
}
