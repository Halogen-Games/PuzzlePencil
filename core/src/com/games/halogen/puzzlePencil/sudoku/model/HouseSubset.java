package com.games.halogen.puzzlePencil.sudoku.model;

import com.games.halogen.puzzlePencil.sudoku.model.grid.Cell;

import java.util.ArrayList;

public class HouseSubset {
    private House house;
    private ArrayList<Cell> specialCells;
    private PenMarks specialMarks;

    public HouseSubset(House house, ArrayList<Cell> specialCells, PenMarks specialMarks){
        this.house = house;
        this.specialCells = specialCells;
        this.specialMarks = specialMarks;
    }

    public Cell getCell(int i){
        return specialCells.get(i);
    }

    public int cellCount(){
        return specialCells.size();
    }

    public PenMarks getSpecialMarks() {
        return specialMarks;
    }
}
