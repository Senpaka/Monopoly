package org.example.ru.vsu.oop.engine.impl.player;

import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DefaultPlayer implements Player {

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
        return this.name;
    }

    @Override
    public List<Street> getStreets() {
        return this.streets;
    }

    @Override
    public void move(int steps) {
        this.position += steps;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public void addMoney(int amount) {
        this.balance += amount;
    }

    @Override
    public int spendMoney(int amount) {
        return this.balance -= amount;
    }

    @Override
    public int getBalance() {
        return this.balance;
    }
}
