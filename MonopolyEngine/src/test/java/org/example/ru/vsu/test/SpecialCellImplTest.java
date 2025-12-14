package org.example.ru.vsu.test;

import org.example.ru.vsu.oop.engine.api.event.Event;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.cells.SpecialCellImpl;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpecialCellImplTest {
    private SpecialCellImpl specialCell;
    private Player player;
    private TestEvent testEvent;

    @BeforeEach
    void setUp() {
        testEvent = new TestEvent();
        specialCell = new SpecialCellImpl(
                "Тестовая клетка",
                "Описание клетки",
                10,
                testEvent
        );
        player = new DefaultPlayer("Тестовый игрок");
    }

    @Test
    void testInitializeState(){
        assertEquals("Тестовая клетка", specialCell.getName());
        assertEquals("Описание клетки", specialCell.getDescription());
        assertEquals(10, specialCell.getPosition());
        assertEquals(CellType.SPECIAL_CELL, specialCell.getCellType());
    }

    @Test
    void testOnLandAppliesEffect(){
        specialCell.onLand(player, null);
        assertTrue(testEvent.effectApplied);
        assertEquals(player, testEvent.lastPlayer);
    }

    @Test
    void testSetPosition(){
        specialCell.setPosition(15);
        assertEquals(15, specialCell.getPosition());
    }

    static class TestEvent implements Event {
        private boolean effectApplied = false;
        private Player lastPlayer;

        @Override
        public void apply(Player player, GameEngine gameEngine) {
            this.effectApplied = true;
            this.lastPlayer = player;
        }

        @Override
        public String getDescription() {
            return "";
        }

        public boolean isEffectApplied() {
            return effectApplied;
        }

        public Player getLastPlayer() {
            return lastPlayer;
        }
    }
}
