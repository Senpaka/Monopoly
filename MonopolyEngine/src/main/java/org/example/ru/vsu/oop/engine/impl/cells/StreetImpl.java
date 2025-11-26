package org.example.ru.vsu.oop.engine.impl.cells;

import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;

public class StreetImpl implements Street {
    /*
    Класс улиц
     */
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

    public StreetImpl(String name, String description, int position, int price, int housePrice, int hotelPrice, int baseRent, ColorGroup colorGroup) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.price = price;
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
        this.baseRent = baseRent;
        this.colorGroup = colorGroup;
    }

    @Override
    public int getPrice() {
        /*
        Возвращает цену улицы
         */
        return this.price;
    }

    @Override
    public int getSellPrice() {
        /*
        Возвращает цену продажи улицы
         */
        return this.price / 2;
    }

    @Override
    public Player getOwner() {
        /*
        Возвращает владельца улицы
         */
        return this.owner;
    }

    @Override
    public void setOwner(Player player) {
        /*
        Устанавливает владельца улицы
         */
        this.owner = player;
    }

    @Override
    public ColorGroup getColorGroup() {
        /*
        Возвращает цвет групп улиц
         */
        return colorGroup;
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
        if (this.owner != null && !this.hasHotel && this.houses < 4){
            this.houses += 1;
        } else if (this.owner != null && !this.hasHotel && this.houses == 4){
            addHotel();
        }
    }

    @Override
    public void addHotel() {
        /*
        Добавляет отель и удаляет дома
         */
        if (this.owner != null && this.houses == 4){
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
    public int getBaseRent() {
        /*
        Возвращает базовую ренту
         */
        return this.baseRent;
    }

    @Override
    public int getRentPrice() {
        /*
        Возвращает пересчитаннцю ренту (с учетом домов и отелей)
         */
        if (this.hasHotel) return this.baseRent + (5 * this.baseRent);
        return this.baseRent + (this.houses * this.baseRent);
    }

    @Override
    public void sellStreetToBank() {
        /*
        Продажа улицы в банк
         */
        owner.addMoney(getSellPrice() + getHousePrice() * this.houses);
        this.owner = null;
        this.houses = 0;
        this.hasHotel = false;

    }

    @Override
    public String getName() {
        /*
        Возвращает имя улицы
         */
        return this.name;
    }

    @Override
    public String getDescription() {
        /*
        Возвращает описание улицы
         */
        return this.description;
    }

    @Override
    public CellType getCellType() {
        /*
        Возвращает тип клетки
         */
        return this.cellType;
    }

    @Override
    public void onLand(Player player, GameEngine gameEngine) {
        /*
        Событие при наступлении на клатку (сделана только оплата ренты)
         */
        if (owner != null && player != owner){
            int rent = getRentPrice();
            player.spendMoney(rent);
            owner.addMoney(rent);
            System.out.println(player.getName() + " Платит игроку " + owner.getName() + "аренду равную " + rent);
        }
    }

    @Override
    public void onPass(Player player, GameEngine gameEngine) {


    }

    @Override
    public int getPosition() {
        /*
        возвращает позицию улицы
         */
        return this.position;
    }

    @Override
    public void setPosition(int position) {
        /*
        устанавливает позицию улицы
         */
        this.position = position;
    }
}
