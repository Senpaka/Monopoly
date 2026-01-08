package org.example.ru.vsu.oop.engine.utils;

public class Dice {
    /*
    Класс кубика
     */
    public int sides;

    public Dice(){
        this.sides = 6;
    }

    public Dice(int sides) {
        if (sides < 2) throw new IllegalArgumentException("У кубика должно быть хотя бы 2 грани");
        this.sides = sides;
    }

    public int roll(){
        /*
        Возвращает выпавшее число очков
         */
        return (int)(Math.random() * sides) + 1;
    }

    public int getSides(){
        return sides;
    }
}
