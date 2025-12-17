package org.example;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.impl.game.GameEngineImpl;
import org.example.ru.vsu.oop.engine.impl.game.GameStateImpl;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.model.events.chance.GoToNCellChanceCard;
import org.example.ru.vsu.oop.engine.model.events.communityChest.BirthdayComCard;
import org.example.ru.vsu.oop.engine.utils.Deck;
import org.example.ru.vsu.oop.engine.utils.InitializeBoard;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {


        ArrayList<Street> streets = new ArrayList<>();
        List<Cell> cells = InitializeBoard.createSortedMonopolyBoard();

        Deque<RandomEvent> cardChance = new ArrayDeque<>(List.of(new GoToNCellChanceCard(3),
                new GoToNCellChanceCard(5),
                new GoToNCellChanceCard(-4)));

        Deque<RandomEvent> cardCommunity = new ArrayDeque<>(List.of(new BirthdayComCard(100),
                new BirthdayComCard(150),
                new BirthdayComCard(50),
                new BirthdayComCard(200)));

        Deck chance = new Deck(cardChance);
        Deck community = new Deck(cardCommunity);

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


        GameStateImpl gameState = new GameStateImpl(players, board, chance, community);
        GameEngineImpl gameEngine = new GameEngineImpl(gameState);
        gameEngine.startGame();
    }
}