package org.example.ru.vsu.oop.engine.impl;

import java.util.List;

public class Player implements org.example.ru.vsu.oop.engine.api.player.Player {

    final String name;
    int position;
    int balance;
    List<Street> streets;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.streets = new List<Street>();
        this.balance = 2500;
    }

    public Player(String name, int position, int balance, List<Street> streets) {
        this.name = name;
        this.position = position;
        this.balance = balance;
        this.streets = streets;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setPosition(int steps) {
        position += steps;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void getMoney(int money) {
        this.balance += money;
    }

    @Override
    public void takeMoney(int cost) {
        this.balance -= cost;
    }

    @Override
    public int getBalance() {
        return balance;
    }
}
