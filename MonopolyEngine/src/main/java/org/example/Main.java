package org.example;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.impl.game.GameEngineImpl;
import org.example.ru.vsu.oop.engine.impl.game.GameStateImpl;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.events.chance.*;
import org.example.ru.vsu.oop.engine.model.events.communityChest.PayMoneyCard;
import org.example.ru.vsu.oop.engine.model.events.communityChest.ReceiveMoneyCard;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.PayMoneyEvent;
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

        Deque<RandomEvent> cardChance = new ArrayDeque<>(List.of(
                new GoToNCellCard(0, "Отправляйтесь на поле «Вперед»"),
                new GoToNCellCard(1, "Отправляйтесь на поле Средиземноморский Проспект"),
                new GoToNCellCard(21, "Отправляйтесь на поле Кентукки Авеню"),
                new GoToClosestPropertyCard(CellType.RAILWAY, "Отправляйтесь на ближайшую железную дорогу"),
                new GoToClosestPropertyCard(CellType.UTILITIES, "Отправляйтесь на ближайшую коммунальную компанию"),
                new GoToNCellCard(11, "Отправляйтесь на поле Площадь Св. Карла"),
                new PayEveryPlayerCard(50, "Вас выбрали председателем правления. Заплатите каждому игроку по 50"),
                new BirthdayComCard(50, "У вас сегодня день рождения. Получите от каждого игрока по 50"),
                new GoToNCellCard(10, "Отправляйтесь в тюрьму. Перейдите прямо в тюрьму. Не проходите «Вперед», не получайте 200 ₽"),
                new MoveToNCellsCard(-3, "Вернитесь на три поля назад"),
                new PayForHousesAndHotelCard(25, 100, "Сделайте ремонт во всей вашей собственности: заплатите по 25 ₽ за каждый дом и по 100 ₽ за каждый отель")));

        Deque<RandomEvent> cardCommunity = new ArrayDeque<>(List.of(
                new ReceiveMoneyCard(200, "Банковская ошибка в вашу пользу. Получите 200 ₽"),
                new ReceiveMoneyCard(20, "Вам пришел налоговый возврат. Получите 20 ₽."),
                new ReceiveMoneyCard(100, "Оплата страховки. Получите 100 ₽."),
                new ReceiveMoneyCard(10, "Вы заняли второе место на конкурсе красоты. Получите 10 ₽."),
                new ReceiveMoneyCard(100, "Рождественский фонд перечисляет вам 100 ₽."),
                new ReceiveMoneyCard(50, "Вы получили доход от акций. Получите 50 ₽."),
                new ReceiveMoneyCard(150, "Возврат займа. Получите 150 ₽."),
                new PayMoneyCard(50, "Оплата гонорара врачу. Заплатите 50 ₽"),
                new PayMoneyCard(100, "Оплата больничного счета. Заплатите 100 ₽."),
                new ReceiveMoneyCard(100, "Вы получили наследство. Получите 100 ₽."),
                new ReceiveMoneyCard(50, "Заплатите за обучение 50 ₽."),
                new ReceiveMoneyCard(50, "Вы продали акции. Получите 50 ₽."),
                new PayMoneyCard(100, "Оплата штрафа за превышение скорости. Заплатите 100 ₽."),
                new PayMoneyCard(150, "Вас обманули во время продажи имущества. Заплатите 150")
        ));

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