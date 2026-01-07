package ru.vsu.oop.ru.vsu.oop.engine.api.game;

import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.impl.board.Board;
import ru.vsu.oop.ru.vsu.oop.engine.utils.Deck;

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
