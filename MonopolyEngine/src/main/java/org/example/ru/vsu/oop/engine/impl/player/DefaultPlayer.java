package org.example.ru.vsu.oop.engine.impl.player;

import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DefaultPlayer implements Player {
    /*
    Класс игрока
     */
    final String name;
    int position;
    int balance;
    List<Street> streets;

    public DefaultPlayer(String name) {
        this.name = name;
        this.position = 0;
        this.streets = new ArrayList<Street>();
        this.balance = 2500;
    }

    public DefaultPlayer(String name, int position, int balance, List<Street> streets) {
        this.name = name;
        this.position = position;
        this.balance = balance;
        this.streets = streets;
    }

    @Override
    public String getName() {
        /*
        Возвращает имя игрока
         */
        return this.name;
    }

    @Override
    public List<Street> getStreets() {
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
