package org.example.ru.vsu.oop.engine.utils;

public class DicePair {
    /*
    Класс двух кубиков
     */
    private Dice dice1;
    private Dice dice2;

    public DicePair(){
        this.dice1 = new Dice();
        this.dice2 = new Dice();
    }

    public DicePair(int sides) {
        this.dice1 = new Dice(sides);
        this.dice2 = new Dice(sides);
    }

    public int roll(){
        /*
        Возвращает сумму выпавших очков на двух кубиках
         */
        return dice1.roll() + dice2.roll();
    }

    public int[] rollSeparately(){
        /*
        Возвращает массив выпавших очков
         */
        return new int[]{dice1.roll(), dice2.roll()};
    }
}
