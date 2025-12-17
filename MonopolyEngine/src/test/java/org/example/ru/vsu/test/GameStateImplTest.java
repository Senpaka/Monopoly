package org.example.ru.vsu.test;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.impl.cells.eventCell.SpecialCellImpl;
import org.example.ru.vsu.oop.engine.impl.game.GameStateImpl;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.ReceiveMoneyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameStateImplTest {

    private GameStateImpl gameState;
    private ArrayList<Player> players;
    private Board board;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>( Arrays.asList(
                new DefaultPlayer("Игрок 1"),
                new DefaultPlayer("Игрок 2"),
                new DefaultPlayer("Игрок 3")
        ));

        List<Cell> cells = Arrays.asList(
                new SpecialCellImpl("Старт", "Начало", 0, new ReceiveMoneyEvent(100))
        );
        board = new Board(cells);

        gameState = new GameStateImpl(players, board);
    }

    @Test
    void testGetCurrentPlayer(){
        assertEquals("Игрок 1", gameState.getCurrentPlayer().getName());
    }

    @Test
    void testNextTurn(){
        gameState.nextTurn();
        assertEquals("Игрок 2", gameState.getCurrentPlayer().getName());

        gameState.nextTurn();
        assertEquals("Игрок 3", gameState.getCurrentPlayer().getName());

        gameState.nextTurn();
        assertEquals("Игрок 1", gameState.getCurrentPlayer().getName());
    }
}
