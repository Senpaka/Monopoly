package org.example.ru.vsu.oop.engine.impl.player;

import org.example.ru.vsu.oop.engine.api.cell.Property;
import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

import java.util.ArrayList;
import java.util.List;

public class DefaultPlayer implements Player {
    /*
    Класс игрока
     */
    final String name;
    int position;
    int balance;
    List<Property> streets;

    public DefaultPlayer(String name) {
        this.name = name;
        this.position = 0;
        this.streets = new ArrayList<Property>();
        this.balance = 2500;
    }

    public DefaultPlayer(String name, int position, int balance, List<Property> streets) {
        this.name = name;
        this.position = position;
        this.balance = balance;
        this.streets = streets;
    }

    @Override
    public int getCountPropertyType(CellType cellType){
        int count = 0;

        for (int i = 0; i < streets.size(); i++) {
            if (this.streets.get(i).getCellType() == cellType){
                count += 1;
            }
        }

        return count;
    }

    @Override
    public String getName() {
        /*
        Возвращает имя игрока
         */
        return this.name;
    }

    @Override
    public void addStreet(Street street){
        streets.add(street);
        street.setOwner(this);
    }

    @Override
    public List<Property> getStreets() {
        /*
        Возвращает список улиц, которыми владеет игрок
         */
        return this.streets;
    }

    @Override
    public void move(int steps) {
        /*
        Передвигает игрока на steps шагов
         */
        this.position += steps;
    }

    @Override
    public void setPosition(int position) {
        /*
        перемещает игрока на указанную позицию
         */
        this.position = position;
    }

    @Override
    public int getPosition() {
        /*
        возвращает позицию игрока
         */
        return this.position;
    }

    @Override
    public void addMoney(int amount) {
        /*
        Добавляет деньги
         */
        this.balance += amount;
    }

    @Override
    public int spendMoney(int amount) {
        /*
        Удаляет деньги
         */
        return this.balance -= amount;
    }

    @Override
    public int getBalance() {
        /*
        Возвращает баланс игрока
         */
        return this.balance;
    }
}
