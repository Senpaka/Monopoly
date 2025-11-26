package org.example.ru.vsu.oop.engine.impl.board;

import org.example.ru.vsu.oop.engine.api.cell.Cell;

import java.util.List;

public class Board {
    private List<Cell> cells;

    public Board(List<Cell> cells) {
        this.cells = cells;
    }

    public Cell getCell(int position){
        return cells.get(position % cells.size());
    }

    public int getSize(){
        return cells.size();
    }
}
