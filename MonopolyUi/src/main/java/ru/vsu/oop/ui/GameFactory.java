package ru.vsu.oop.ui;

import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameState;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.impl.board.Board;
import ru.vsu.oop.ru.vsu.oop.engine.impl.game.GameStateImpl;
import ru.vsu.oop.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import ru.vsu.oop.ru.vsu.oop.engine.utils.Deck;
import ru.vsu.oop.ru.vsu.oop.engine.utils.InitializeBoard;

import java.util.List;

public class GameFactory {

    public static GameState createGameState() {
        Board board = InitializeBoard.createSortedMonopolyBoard();

        List<Player> players = List.of(
                new DefaultPlayer("Игрок 1"),
                new DefaultPlayer("Игрок 2")
        );

        Deck chance = InitializeBoard.createChanceDeck();
        Deck community = InitializeBoard.createCommunityDeck();

        return new GameStateImpl(players, board, chance, community);
    }
}

