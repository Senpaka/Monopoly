package org.example;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.impl.cells.SpecialCellImpl;
import org.example.ru.vsu.oop.engine.impl.cells.StreetImpl;
import org.example.ru.vsu.oop.engine.impl.game.GameEngineImpl;
import org.example.ru.vsu.oop.engine.impl.game.GameStateImpl;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;
import org.example.ru.vsu.oop.engine.model.events.PayMoneyEvent;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(new StreetImpl("dfdfd", "test", 0, 100, 30, 40, 10, ColorGroup.BROWN));
        cells.add(new SpecialCellImpl("special", "уберет деньги 1000", 1, new PayMoneyEvent(1000)));
        cells.add(new SpecialCellImpl("special слебее", "уберет деньги 500", 2, new PayMoneyEvent(500)));
        Board board = new Board(cells);

        ArrayList<Player> players = new ArrayList<>();
        DefaultPlayer player = new DefaultPlayer("sen");
        DefaultPlayer player1 = new DefaultPlayer("sen1");
        players.add(player);
        players.add(player1);


        GameStateImpl gameState = new GameStateImpl(players, board);
        GameEngineImpl gameEngine = new GameEngineImpl(gameState);
        gameEngine.startGame();
    }
}