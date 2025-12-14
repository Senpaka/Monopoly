package org.example.ru.vsu.oop.engine.utils;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.impl.cells.SpecialCellImpl;
import org.example.ru.vsu.oop.engine.impl.cells.Property.StreetImpl;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.GoToJailEvent;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.NoEffectEvent;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.PayMoneyEvent;
import org.example.ru.vsu.oop.engine.model.events.staticEvents.ReceiveMoneyEvent;

import java.util.ArrayList;
import java.util.List;

public class InitializeBoard {
    public static List<Cell> createSortedMonopolyBoard() {
        List<Cell> cells = new ArrayList<>(40);

        // Инициализируем все клетки (0-39)
        for (int i = 0; i < 40; i++) {
            cells.add(null);
        }

        // Специальные клетки
        cells.set(0, new SpecialCellImpl("Старт", "Получите 200$ при прохождении", 0,
                new ReceiveMoneyEvent(200)));
        cells.set(2, new SpecialCellImpl("Общественная казна", "Взять карту из колоды", 2,
                null));
        cells.set(4, new SpecialCellImpl("Налог на доход", "Заплатите 200$", 4,
                new PayMoneyEvent(200)));
        cells.set(5, new SpecialCellImpl("Южная Железная Дорога", "Железнодорожная станция", 5,
                null));
        cells.set(7, new SpecialCellImpl("Шанс", "Взять карту из колоды", 7,
                null));
        cells.set(10, new SpecialCellImpl("Тюрьма/Просто посетите", "Тюрьма", 10,
                new NoEffectEvent()));
        cells.set(12, new SpecialCellImpl("Электрическая Компания", "Коммунальное предприятие", 12,
                null));
        cells.set(15, new SpecialCellImpl("Пенсильванская Железная Дорога", "Железнодорожная станция", 15,
                null));
        cells.set(17, new SpecialCellImpl("Общественная казна", "Взять карту из колоды", 17,
                null));
        cells.set(20, new SpecialCellImpl("Бесплатная Парковка", "Отдых без оплаты", 20,
                new NoEffectEvent()));
        cells.set(22, new SpecialCellImpl("Шанс", "Взять карту из колоды", 22,
                null));
        cells.set(25, new SpecialCellImpl("Бродвейская Железная Дорога", "Железнодорожная станция", 25,
                null));
        cells.set(28, new SpecialCellImpl("Водопроводная Компания", "Коммунальное предприятие", 28,
                null));
        cells.set(30, new SpecialCellImpl("Отправляйтесь в тюрьму", "Переместитесь в тюрьму", 30,
                new GoToJailEvent()));
        cells.set(33, new SpecialCellImpl("Общественная казна", "Взять карту из колоды", 33,
                null));
        cells.set(35, new SpecialCellImpl("Западная Железная Дорога", "Железнодорожная станция", 35,
                null));
        cells.set(36, new SpecialCellImpl("Шанс", "Взять карту из колоды", 36,
                null));
        cells.set(38, new SpecialCellImpl("Налог на роскошь", "Заплатите 100$", 38,
                new PayMoneyEvent(100)));

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

        return result;
    }
}
