package org.example;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.impl.game.GameEngineImpl;
import org.example.ru.vsu.oop.engine.impl.game.GameStateImpl;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.utils.InitializeBoard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {


        ArrayList<Street> streets = new ArrayList<>();
        List<Cell> cells = InitializeBoard.createSortedMonopolyBoard();

        for (int i = 0; i < cells.size(); i++) {
            System.out.println(cells.get(i).getName());
        }

        System.out.println(streets);
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