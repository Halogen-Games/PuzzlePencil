package com.games.halogen.puzzlePencil.sudoku.model;

import com.games.halogen.gameEngine.utils.Pair.IntPair;

import java.util.ArrayList;

/*
A cell values are edited by parent grid.
Pencil marks are updated on cell value update
 */
public class Cell {
    private int dimensions;

    private int num;
    private ArrayList<House> houses;
    private IntPair coordinates;

    private PenMarks penMarks;
    private PenMarks visibleMarks;

    public Cell(IntPair coordinates, int dimensions) {
        this.coordinates = coordinates;
        this.dimensions = dimensions;

        setupPenMarks();
    }

    private void setupPenMarks() {
        penMarks = new PenMarks();
        visibleMarks = new PenMarks();

        for(int i=0; i<dimensions; i++){
            penMarks.addMark(i+1);
        }
    }

    public PenMarks getPenMarks() {
        return penMarks;
    }

    public PenMarks getVisibleMarks() {
        return visibleMarks;
    }

    public IntPair getCoordinates(){
        return this.coordinates;
    }

    /*
    IMPORTANT: This should only be called from grid
     */
    void setValue(Integer num) {
        this.num = num;
    }

    void clearValue() {
        this.num = -1;
    }
}
