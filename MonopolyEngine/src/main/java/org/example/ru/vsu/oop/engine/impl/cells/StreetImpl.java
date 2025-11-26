package org.example.ru.vsu.oop.engine.impl.cells;

import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;

public class StreetImpl implements Street {
    private String name;
    private String description;
    private int position;
    private CellType cellType = CellType.STREET;

    private int price;
    private int housePrice;
    private int hotelPrice;
    private int baseRent;

    private ColorGroup colorGroup;

    private Player owner;
    private int houses = 0;
    private boolean hasHotel = false;

    public StreetImpl(String name, String description, int position, CellType cellType, int price, int housePrice, int hotelPrice, int baseRent, ColorGroup colorGroup) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.cellType = cellType;
        this.price = price;
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
        this.baseRent = baseRent;
        this.colorGroup = colorGroup;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public int getSellPrice() {
        return this.price / 2;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }

    @Override
    public ColorGroup getColorGroup() {
        return colorGroup;
    }

    @Override
    public int getHouses() {
        return this.houses;
    }

    @Override
    public boolean hasHotel() {
        return this.hasHotel;
    }

    @Override
    public void addHouse() {
        if (!this.hasHotel && this.houses < 4){
            this.houses += 1;
        } else if (!this.hasHotel && this.houses == 4){
            addHotel();
        }
    }

    @Override
    public void addHotel() {
        this.hasHotel = true;
        this.houses = 0;
    }

    @Override
    public int getHousePrice() {
        return this.housePrice;
    }

    @Override
    public int getHotelPrice() {
        return this.hotelPrice;
    }

    @Override
    public int getBaseRent() {
        return this.baseRent;
    }

    @Override
    public int getRentPrice() {
        if (this.hasHotel) return this.baseRent * 5;
        return this.baseRent + (this.houses * this.baseRent);
    }

    @Override
    public void sellStreetToBank() {
        owner.addMoney(getSellPrice() + getHousePrice() * this.houses);
        this.owner = null;
        this.houses = 0;
        this.hasHotel = false;

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public CellType getCellType() {
        return this.cellType;
    }

    @Override
    public void onLand(Player player, GameEngine engine) {
        if (owner != null && player != owner){
            int rent = getRentPrice();
            player.spendMoney(rent);
            owner.addMoney(rent);
            System.out.println(player.getName() + " Платит игроку " + owner.getName() + "аренду равную " + rent);
        }
    }

    @Override
    public void onPass(Player player, GameEngine engine) {

    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }
}
