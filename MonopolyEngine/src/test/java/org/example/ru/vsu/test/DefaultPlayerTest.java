package org.example.ru.vsu.test;

import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.impl.cells.property.StreetImpl;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DefaultPlayerTest {
    private DefaultPlayer player;

    @BeforeEach
    void setUp() {
        player = new DefaultPlayer("Тестовый игрок");
    }

    @Test
    void testInitializeState(){
        assertEquals("Тестовый игрок", player.getName());
        assertEquals(0, player.getPosition());
        assertEquals(2500, player.getBalance());
        assertTrue(player.getStreets().isEmpty());
    }

    @Test
    void testCustomConstructor() {
        List<Street> streets = new ArrayList<>();
        streets.add(new StreetImpl("Улица", "Описание", 1, 100, 50, 100, 10, ColorGroup.BROWN));

        DefaultPlayer customPlayer = new DefaultPlayer("Кастомный", 5, 1000, streets);

        assertEquals("Кастомный", customPlayer.getName());
        assertEquals(5, customPlayer.getPosition());
        assertEquals(1000, customPlayer.getBalance());
        assertEquals(1, customPlayer.getStreets().size());
    }

    @Test
    void testMove(){
        player.move(5);
        assertEquals(5, player.getPosition());

        player.move(12);
        assertEquals(17, player.getPosition());
    }

    @Test
    void testSetPosition(){
        player.setPosition(123456);
        assertEquals(123456, player.getPosition());
    }

    @Test
    void testAddSpendMoney(){
        player.addMoney(1000);
        assertEquals(3500, player.getBalance());

        player.spendMoney(2000);
        assertEquals(1500, player.getBalance());

        player.spendMoney(3000);
        assertEquals(-1500, player.getBalance()); // так должно быть для возможности продовать недвижку при отриц балансе
    }

    @Test
    void testGetStreets(){
        Street street1 = new StreetImpl("Улица1", "Описание1", 1, 100, 50, 100, 10, ColorGroup.BROWN);
        Street street2 = new StreetImpl("Улица2", "Описание2", 2, 200, 20, 200, 20, ColorGroup.BROWN);

        player.addStreet(street1);
        player.addStreet(street2);

        assertEquals(2, player.getStreets().size());
        assertEquals(street1, player.getStreets().getFirst());
    }
}
