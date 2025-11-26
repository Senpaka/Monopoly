package org.example.ru.vsu.test;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.game.GameState;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.impl.cells.SpecialCellImpl;
import org.example.ru.vsu.oop.engine.impl.cells.StreetImpl;
import org.example.ru.vsu.oop.engine.impl.game.GameEngineImpl;
import org.example.ru.vsu.oop.engine.impl.game.GameStateImpl;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.model.events.PayMoneyEvent;
import org.example.ru.vsu.oop.engine.model.events.ReceiveMoneyEvent;
import org.example.ru.vsu.oop.engine.utils.DicePair;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class GameEngineImplTest {

    private GameEngine gameEngine;
    private GameState gameState;
    private Board board;
    private TestDicePair testDice;

    @BeforeEach
    void setUp() {
        List<Cell> cells = Arrays.asList(
                new SpecialCellImpl("Старт", "Начальная клетка", 0, new ReceiveMoneyEvent(100)),
                new StreetImpl("Улица 1", "Первая улица", 1, 100, 50, 100, 10, ColorGroup.BROWN),
                new SpecialCellImpl("Финиш", "Конечная клетка", 2, new PayMoneyEvent(100))
        );
        board = new Board(cells);

        List<Player> players = Arrays.asList(
                new DefaultPlayer("Игрок 1"),
                new DefaultPlayer("Игрок 2")
        );

        gameState = new GameStateImpl(players, board);
        gameEngine = new GameEngineImpl(gameState);

        testDice = new TestDicePair();
    }

    @Test
    void testMovePlayer(){
        Player player = gameState.getPlayers().get(0);

        gameEngine.movePlayer(player, 3);
        assertEquals(0, player.getPosition());

        gameEngine.movePlayer(player, 2);
        assertEquals(2, player.getPosition());
    }

    @Test
    void testGameOver(){
        assertFalse(gameEngine.isGameOver());

        Player bankruptPlayer = new DefaultPlayer("Банкрот");
        bankruptPlayer.spendMoney(3000); // Баланс станет -500

        List<Player> players = Arrays.asList(
                bankruptPlayer,
                new DefaultPlayer("Богатый игрок")
        );

        GameState bankruptState = new GameStateImpl(players, board);
        GameEngine bankruptEngine = new GameEngineImpl(bankruptState);

        assertTrue(bankruptEngine.isGameOver());
    }

    @Test
    void testWinner(){
        Player player1 = gameState.getPlayers().get(0);
        Player player2 = gameState.getPlayers().get(1);
        player2.spendMoney(1000);

        assertEquals(player1, gameEngine.getWinner());
    }

    static class TestDicePair extends DicePair {
        private int fixedResult = 1;

        public void setFixedResult(int result) {
            this.fixedResult = result;
        }

        @Override
        public int roll() {
            return fixedResult;
        }
    }
}
