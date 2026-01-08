package org.example.ru.vsu.test;

import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.cells.StreetImpl;
import org.example.ru.vsu.oop.engine.impl.player.DefaultPlayer;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StreetImplTest {
    private StreetImpl street;
    private Player owner;
    private Player otherPlayer;

    @BeforeEach
    void setUp() {
        street = new StreetImpl(
                "Тестовая улица",
                "Описание улицы",
                5,
                200,  // price
                50,   // housePrice
                100,  // hotelPrice
                20,   // baseRent
                ColorGroup.RED
        );

        owner = new DefaultPlayer("Владелец");
        otherPlayer = new DefaultPlayer("Другой игрок");
    }

    @Test
    void testInitializationPlayer(){
        assertEquals("Тестовая улица", street.getName());
        assertEquals("Описание улицы", street.getDescription());
        assertEquals(5, street.getPosition());
        assertEquals(200, street.getPrice());
        assertEquals(50, street.getHousePrice());
        assertEquals(100, street.getHotelPrice());
        assertEquals(20, street.getBaseRent());
        assertEquals(CellType.STREET, street.getCellType());
    }

    @Test
    void testSetOwner(){
        street.setOwner(owner);
        assertEquals(owner, street.getOwner());
    }

    @Test
    void testAddHouseHotel(){
        street.setOwner(owner);

        street.addHouse();
        assertEquals(1, street.getHouses());

        street.addHouse();
        street.addHouse();
        street.addHouse();
        assertEquals(4, street.getHouses());

        street.addHouse();
        assertTrue(street.hasHotel());
        assertEquals(0, street.getHouses());
    }

    @Test
    void testAddHotelWithoutHouses(){
        street.setOwner(owner);
        street.addHotel();

        assertFalse(street.hasHotel());
    }

    @Test
    void testAddHouseWithoutOwner(){
        street.addHouse();

        assertEquals(0, street.getHouses());
    }

    @Test
    void getRentPrice(){
        street.setOwner(owner);

        assertEquals(20, street.getRentPrice());

        street.addHouse();
        assertEquals(40, street.getRentPrice());

        street.addHouse();
        assertEquals(60, street.getRentPrice());

        street.addHouse();
        street.addHouse();
        street.addHotel();
        assertEquals(120, street.getRentPrice());
    }

    @Test
    void onLandWithOwner(){
        street.setOwner(owner);
        owner.addMoney(1000);
        otherPlayer.addMoney(1000);

        int ownerBalance = owner.getBalance();
        int otherPlayerBalance = otherPlayer.getBalance();

        street.onLand(otherPlayer, null);

        assertEquals(otherPlayerBalance - 20, otherPlayer.getBalance());
        assertEquals(ownerBalance + 20, owner.getBalance());
    }

    @Test
    void testOnLandWithoutOwner() {
        street.onLand(otherPlayer, null);
        assertEquals(2500, otherPlayer.getBalance());
    }

    @Test
    void testOnLandSelfStreet(){
        street.setOwner(owner);

        street.onLand(owner, null);
        assertEquals(2500, owner.getBalance());
    }
}
