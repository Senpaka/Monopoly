package org.example.ru.vsu.oop.engine.impl.game;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameState;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;

import java.util.List;
import java.util.Queue;

public class GameStateImpl implements GameState {

    private List<Player> players;
    private int currentPlayerIndex;
    private Board board;
//    private Queue<RandomEvent> randomEvents;

    public GameStateImpl(List<Player> players, Board board) { //, Queue<RandomEvent> randomEvents
        this.players = players;
        this.board = board;
        //this.randomEvents = randomEvents;
    }

    @Override
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    @Override
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public Board getBoard() {
        return board;
    }

//    @Override
//    public RandomEvent drawRandomEvent() {
//        RandomEvent event = randomEvents.poll();
//        randomEvents.offer(event);
//        return event;
//    }
}
