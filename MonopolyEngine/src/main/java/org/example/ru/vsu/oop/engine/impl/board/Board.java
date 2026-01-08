package org.example.ru.vsu.oop.engine.impl.board;

import org.example.ru.vsu.oop.engine.api.cell.Cell;

import java.util.List;

public class Board {
    /*
    Класс игральной доски
     */
    private List<Cell> cells;

    public Board(List<Cell> cells) {
        this.cells = cells;
    }

    public Cell getCell(int position){
        /*
        Возвращает клетку на которой стоит игрок (если позиция больше клетки то берет остаток от деления)
         */
        return cells.get(position % cells.size());
    }

    public List<Cell> getCells(){
        return cells;
    }

    public int getSize(){
        /*
        Возвращает размер поля
         */
        return cells.size();
    }
}
