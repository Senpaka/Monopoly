package org.example.ru.vsu.oop.engine.impl.cells.property;

import org.example.ru.vsu.oop.engine.api.cell.Property;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public abstract class PropertyImpl implements Property {

    private final String name;
    private final String description;
    private int position;

    protected final int price;

    protected Player owner;

    public PropertyImpl(String name, String description, int position, int price) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.price = price;
    }

    @Override
    public boolean hasOwner(){
        return this.owner != null;
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
    public void onLand(Player player, GameEngine gameEngine) {
        /*
        Событие при наступлении на клатку (сделана только оплата ренты)
         */
        if (owner != null && player != owner){
            int rent = getRentPrice();
            player.spendMoney(rent);
            owner.addMoney(rent);
            System.out.println(player.getName() + " Платит игроку " + owner.getName() + "аренду равную " + rent);

        }else if (owner == null){
            System.out.println("Хотите приобрести" + name);
            boolean isBuy = true; //Временно

            if (isBuy && player.spendMoney(price)){
                player.addProperty(this);
                this.owner = player;
                gameEngine.addMessage("Покупка совершена");
            }else{
                gameEngine.addMessage("Недостаточно средств");
            }
        }
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

    @Override
    public abstract int getRentPrice();

    @Override
    public abstract void sellToBank();

    @Override
    public abstract CellType getCellType();

    @Override
    public abstract void onPass(Player player, GameEngine gameEngine);
}
