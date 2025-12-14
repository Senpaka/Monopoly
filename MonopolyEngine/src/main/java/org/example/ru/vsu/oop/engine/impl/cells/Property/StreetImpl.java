package org.example.ru.vsu.oop.engine.impl.cells.Property;

import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;

public class StreetImpl extends PropertyImpl implements Street {
    /*
    Класс улиц
     */
    private final int housePrice;
    private final int hotelPrice;

    private final ColorGroup colorGroup;

    private int houses = 0;
    private boolean hasHotel = false;

    private final int HOTEL_MULTIPLIER = 5;

    public StreetImpl(String name, String description, int position, int price, int housePrice, int hotelPrice, int baseRent, ColorGroup colorGroup) {
        super(name, description, position, price, baseRent);
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
        this.colorGroup = colorGroup;
    }

    @Override
    public ColorGroup getColorGroup() {
        /*
        Возвращает цвет групп улиц
         */
        return this.colorGroup;
    }

    @Override
    public int getHouses() {
        /*
        Возвращает кол-во домов
         */
        return this.houses;
    }

    @Override
    public boolean hasHotel() {
        /*
        Возвращает присутствует ли на улице отель
         */
        return this.hasHotel;
    }

    @Override
    public void addHouse() {
        /*
        Добовляет дом, если домов меньше 4, иначе добовляет отель
         */
        if (super.hasOwner() && !this.hasHotel && this.houses < 4){
            this.houses += 1;
        } else if (super.hasOwner() && !this.hasHotel && this.houses == 4){
            addHotel();
        }
    }

    @Override
    public void addHotel() {
        /*
        Добавляет отель и удаляет дома
         */
        if (super.hasOwner() && this.houses == 4){
            this.hasHotel = true;
            this.houses = 0;
        }
    }

    @Override
    public int getHousePrice() {
        /*
        Возвращает цену дома
         */
        return this.housePrice;
    }

    @Override
    public int getHotelPrice() {
        /*
        Возвращает цену отеля
         */
        return this.hotelPrice;
    }

    @Override
    public int getRentPrice() {
        /*
        Возвращает пересчитаннцю ренту (с учетом домов и отелей)
         */
        if (this.hasHotel) return super.baseRent + (this.HOTEL_MULTIPLIER * super.baseRent);
        return super.baseRent + (this.houses * super.baseRent);
    }

    @Override
    public void sellToBank() {
        /*
        Продажа улицы в банк
         */
        super.owner.addMoney(getSellPrice() + getHousePrice() * this.houses);
        super.owner = null;
        this.houses = 0;
        this.hasHotel = false;
    }

    @Override
    public CellType getCellType() {
        return CellType.STREET;
    }

    @Override
    public void onPass(Player player, GameEngine gameEngine) {

    }
}
