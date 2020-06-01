package com.games.halogen.puzzlePencil.sudoku.model;

import com.games.halogen.puzzlePencil.sudoku.model.grid.Cell;

import java.util.ArrayList;

public class House {
    private HouseType type;
    private ArrayList<Cell> cells;

    public House(HouseType type) {
        this.type = type;
        cells = new ArrayList<>();
    }

    /*
    Returns cells in this house which have the given pencil mark
     */
    public ArrayList<Cell> getCellsWithMark(int mark) {
        //todo fill
        return new ArrayList<>();
    }

    public ArrayList<Cell> getCellsWithMarkCount(int i) {
        //todo fill
        return new ArrayList<>();
    }

    public boolean hasCellWithNum(int num){
        for(Cell c:cells){
            if(c.getValue() == num){
                return true;
            }
        }

        return false;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public HouseType getType() {
        return type;
    }

    public void removePencilMarkFromCells(Integer num) {
        for(Cell c:cells){
            c.getPenMarks().removeMark(num);
        }
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }
}
