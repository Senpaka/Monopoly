package org.example.ru.vsu.oop.engine.model.events.chance;

import org.example.ru.vsu.oop.engine.api.cell.Property;
import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class PayForHousesAndHotelCard implements RandomEvent {

    private int housesPrice;
    private int hotelPrice;
    private String description;

    public PayForHousesAndHotelCard(int housesPrice, int hotelPrice, String description) {
        this.housesPrice = housesPrice;
        this.hotelPrice = hotelPrice;
        this.description = description;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        int totalPrice = 0;

        for(Property property: player.getProperty()){
            if (property.getCellType() == CellType.STREET){
                Street street = (Street) property;

                if (street.hasHotel()){
                    totalPrice += this.hotelPrice;
                }else{
                    totalPrice += street.getHouses() * this.housesPrice;
                }
            }
        }

        player.spendMoney(totalPrice);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public CellType getType() {
        return CellType.CHANCE;
    }
}
