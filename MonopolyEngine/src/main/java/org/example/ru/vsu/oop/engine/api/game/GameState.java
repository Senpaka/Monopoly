package org.example.ru.vsu.oop.engine.api.game;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.utils.Deck;

import java.util.List;

public interface GameState {
    /*
    Интерфейс состояния игры
     */
    Player getCurrentPlayer();
    void nextTurn();
    List<Player> getPlayers();
    Board getBoard();
    Deck getChance();
    Deck getCommunity();
    //RandomEvent drawRandomEvent();

}
