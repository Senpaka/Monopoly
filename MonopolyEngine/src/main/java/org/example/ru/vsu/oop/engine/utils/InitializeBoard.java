package org.example.ru.vsu.oop.engine.utils;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.impl.cells.eventCell.CardCellImpl;
import org.example.ru.vsu.oop.engine.impl.cells.property.RailwayImpl;
import org.example.ru.vsu.oop.engine.impl.cells.property.UtilitiesImpl;
import org.example.ru.vsu.oop.engine.impl.cells.eventCell.SpecialCellImpl;
import org.example.ru.vsu.oop.engine.impl.cells.property.StreetImpl;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;
import org.example.ru.vsu.oop.engine.model.events.chance.*;
import org.example.ru.vsu.oop.engine.model.events.communityChest.PayMoneyCard;
import org.example.ru.vsu.oop.engine.model.events.communityChest.ReceiveMoneyCard;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.GoToJailEvent;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.NoEffectEvent;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.PayMoneyEvent;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.ReceiveMoneyEvent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class InitializeBoard {
    public static Board createSortedMonopolyBoard() {
        List<Cell> cells = new ArrayList<>(40);

        // Инициализируем все клетки (0-39)
        for (int i = 0; i < 40; i++) {
            cells.add(null);
        }

        // Специальные клетки
        cells.set(0, new SpecialCellImpl("Старт", "Получите 200$ при прохождении", 0,
                new ReceiveMoneyEvent(200)));

        cells.set(4, new SpecialCellImpl("Налог на доход", "Заплатите 200$", 4,
                new PayMoneyEvent(200)));

        cells.set(10, new SpecialCellImpl("Тюрьма/Просто посетите", "Тюрьма", 10,
                new NoEffectEvent()));

        cells.set(20, new SpecialCellImpl("Бесплатная Парковка", "Отдых без оплаты", 20,
                new NoEffectEvent()));

        cells.set(30, new SpecialCellImpl("Отправляйтесь в тюрьму", "Переместитесь в тюрьму", 30,
                new GoToJailEvent()));

        cells.set(38, new SpecialCellImpl("Налог на роскошь", "Заплатите 100$", 38,
                new PayMoneyEvent(100)));

        // Карты
        cells.set(33, new CardCellImpl("Общественная казна", "Взять карту из колоды", 33,
                CellType.COMMUNITY));
        cells.set(36, new CardCellImpl("Шанс", "Взять карту из колоды", 36,
                CellType.CHANCE));
        cells.set(22, new CardCellImpl("Шанс", "Взять карту из колоды", 22,
                CellType.CHANCE));
        cells.set(17, new CardCellImpl("Общественная казна", "Взять карту из колоды", 17,
                CellType.COMMUNITY));
        cells.set(7, new CardCellImpl("Шанс", "Взять карту из колоды", 7,
                CellType.CHANCE));
        cells.set(2, new CardCellImpl("Общественная казна", "Взять карту из колоды", 2,
                CellType.COMMUNITY));

        // Железные дороги
        cells.set(5, new RailwayImpl("Южная Железная Дорога", "Железнодорожная станция", 5, 200, 50));
        cells.set(25, new RailwayImpl("Бродвейская Железная Дорога", "Железнодорожная станция", 25, 200, 50));
        cells.set(35, new RailwayImpl("Западная Железная Дорога", "Железнодорожная станция", 35, 200, 50));
        cells.set(15, new RailwayImpl("Пенсильванская Железная Дорога", "Железнодорожная станция", 15, 200, 50));

        // Комунальные услуги
        cells.set(12, new UtilitiesImpl("Электрическая Компания", "Коммунальное предприятие", 12, 250));
        cells.set(28, new UtilitiesImpl("Водопроводная Компания", "Коммунальное предприятие", 28, 250));

        // Улицы коричневой группы
        cells.set(1, new StreetImpl("Средиземноморский Проспект", "Коричневая группа", 1,
                60, 50, 50, 2, ColorGroup.BROWN));
        cells.set(3, new StreetImpl("Балтийский Проспект", "Коричневая группа", 3,
                60, 50, 50, 4, ColorGroup.BROWN));

        // Улицы светло-синей группы
        cells.set(6, new StreetImpl("Ориентал Авеню", "Светло-синяя группа", 6,
                100, 50, 50, 6, ColorGroup.LIGHT_BLUE));
        cells.set(8, new StreetImpl("Вермонт Авеню", "Светло-синяя группа", 8,
                100, 50, 50, 6, ColorGroup.LIGHT_BLUE));
        cells.set(9, new StreetImpl("Коннектикут Авеню", "Светло-синяя группа", 9,
                120, 50, 50, 8, ColorGroup.LIGHT_BLUE));

        // Улицы розовой группы
        cells.set(11, new StreetImpl("Площадь Св. Карла", "Розовая группа", 11,
                140, 100, 100, 10, ColorGroup.PINK));
        cells.set(13, new StreetImpl("Штаты Авеню", "Розовая группа", 13,
                140, 100, 100, 10, ColorGroup.PINK));
        cells.set(14, new StreetImpl("Вирджиния Авеню", "Розовая группа", 14,
                160, 100, 100, 12, ColorGroup.PINK));

        // Улицы оранжевой группы
        cells.set(16, new StreetImpl("Площадь Св. Джеймса", "Оранжевая группа", 16,
                180, 100, 100, 14, ColorGroup.ORANGE));
        cells.set(18, new StreetImpl("Теннесси Авеню", "Оранжевая группа", 18,
                180, 100, 100, 14, ColorGroup.ORANGE));
        cells.set(19, new StreetImpl("Нью-Йорк Авеню", "Оранжевая группа", 19,
                200, 100, 100, 16, ColorGroup.ORANGE));

        // Улицы красной группы
        cells.set(21, new StreetImpl("Кентукки Авеню", "Красная группа", 21,
                220, 150, 150, 18, ColorGroup.RED));
        cells.set(23, new StreetImpl("Индиана Авеню", "Красная группа", 23,
                220, 150, 150, 18, ColorGroup.RED));
        cells.set(24, new StreetImpl("Иллинойс Авеню", "Красная группа", 24,
                240, 150, 150, 20, ColorGroup.RED));

        // Улицы желтой группы
        cells.set(26, new StreetImpl("Атлантик Авеню", "Желтая группа", 26,
                260, 150, 150, 22, ColorGroup.YELLOW));
        cells.set(27, new StreetImpl("Вентнор Авеню", "Желтая группа", 27,
                260, 150, 150, 22, ColorGroup.YELLOW));
        cells.set(29, new StreetImpl("Марвин Гарденс", "Желтая группа", 29,
                280, 150, 150, 24, ColorGroup.YELLOW));

        // Улицы зеленой группы
        cells.set(31, new StreetImpl("Тихоокеанский Проспект", "Зеленая группа", 31,
                300, 200, 200, 26, ColorGroup.GREEN));
        cells.set(32, new StreetImpl("Северная Каролина Авеню", "Зеленая группа", 32,
                300, 200, 200, 26, ColorGroup.GREEN));
        cells.set(34, new StreetImpl("Пенсильвания Авеню", "Зеленая группа", 34,
                320, 200, 200, 28, ColorGroup.GREEN));

        // Улицы темно-синей группы
        cells.set(37, new StreetImpl("Парк Плейс", "Темно-синяя группа", 37,
                350, 200, 200, 35, ColorGroup.DARK_BLUE));
        cells.set(39, new StreetImpl("Бродвей", "Темно-синяя группа", 39,
                400, 200, 200, 50, ColorGroup.DARK_BLUE));

        // Удаляем null, если нужен только список без пустых ячеек
        List<Cell> result = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell != null) {
                result.add(cell);
            }
        }

        Board board = new Board(cells);

        return board;
    }

    public static Deck createChanceDeck() {
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

        return new Deck((cardChance));
    }

    public static Deck createCommunityDeck(){
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

        return new Deck(cardCommunity);
    }

}
