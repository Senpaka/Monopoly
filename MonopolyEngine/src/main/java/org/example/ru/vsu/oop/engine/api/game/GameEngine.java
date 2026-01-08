package org.example.ru.vsu.oop.engine.api.game;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.event.Event;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

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
    RandomEvent drawRandomEvent(CellType cellType);
    GameListener getListener();
    public void onCellLanded(Player player, Cell cell);
    public void onCardDrawn(Player player, Event event);

        Player getCurrentPlayer();
}
