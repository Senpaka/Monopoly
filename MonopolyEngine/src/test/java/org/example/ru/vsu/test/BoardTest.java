package org.example.ru.vsu.test;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.impl.board.Board;
import org.example.ru.vsu.oop.engine.impl.cells.SpecialCellImpl;
import org.example.ru.vsu.oop.engine.impl.cells.StreetImpl;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;
import org.example.ru.vsu.oop.engine.model.events.PayMoneyEvent;
import org.example.ru.vsu.oop.engine.model.events.ReceiveMoneyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {
    private Board board;
    private List<Cell> cells;

    @BeforeEach
    void setUp() {
        cells = Arrays.asList(
                new SpecialCellImpl("Старт", "Начальная событие", 0, new PayMoneyEvent(1000)),
                new StreetImpl("Улица 1", "Тестовая улица", 1, 100, 50, 100, 10, ColorGroup.BROWN),
                new SpecialCellImpl("Событие", "Клетка событие", 2, new ReceiveMoneyEvent(100))
        );
        board = new Board(cells);
    }

    @Test
    void testGetCell(){
        Cell cell = board.getCell(1);
        assertEquals("Улица 1", cell.getName());
        assertEquals(1, cell.getPosition());
    }

    @Test
    void testGetCellWithWrapAround(){
        Cell cell = board.getCell(5); // 5 % 3 = 2
        assertEquals("Событие", cell.getName());
        assertEquals(2, cell.getPosition());
    }

    @Test
    void testGetSize(){
        assertEquals(3, board.getSize());
    }
}
