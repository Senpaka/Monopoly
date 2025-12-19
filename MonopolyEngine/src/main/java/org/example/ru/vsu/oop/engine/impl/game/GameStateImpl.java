package org.example.ru.vsu.oop.engine.impl.game;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameState;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.utils.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GameStateImpl implements GameState {
    /*
    Класс хранящий состояние игры
     */
    private List<Player> players;
    private int currentPlayerIndex;
    private Board board;
    private Deck chance;
    private Deck community;

    public GameStateImpl(List<Player> players, Board board, Deck chance, Deck community) {

        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Список игроков не может быть пустым");
        }
        if (board == null) {
            throw new IllegalArgumentException("Игровое поле не может быть null");
        }

        this.players = players;
        this.board = board;
        this.community = community;
        this.chance = chance;

        if (chance != null) chance.shuffle();
        if (community != null) community.shuffle();
    }

    @Override
    public Player getCurrentPlayer() {
        /*
        Возвращает игрока у которого сейчас ход
         */
        return players.get(currentPlayerIndex);
    }

    @Override
    public void nextTurn() {
        /*
        Меняет ход на следущего игрока
         */
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    @Override
    public List<Player> getPlayers() {
        /*
        Возвращает список игроков
         */
        return players;
    }

    @Override
    public Board getBoard() {
        /*
        Возвращает игровое поле
         */
        return board;
    }

    @Override
    public Deck getChance() {
        return chance;
    }

    @Override
    public Deck getCommunity() {
        return community;
    }
}
