package com.games.halogen.puzzlePencil.sudoku.generator.model;

import java.util.ArrayList;
import java.util.BitSet;

public class HouseSubset {
    private House house;
    private ArrayList<Cell> cells;

    public HouseSubset(House house, ArrayList<Cell> cells){
        this.house = house;
        this.cells = cells;
    }

    public Cell get(int i){
        return cells.get(i);
    }

    public int size(){
        return cells.size();
    }
}
