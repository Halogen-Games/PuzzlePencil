package com.games.halogen.puzzlePencil.sudoku.model.grid;

import com.games.halogen.gameEngine.utils.Pair.IntPair;
import com.games.halogen.puzzlePencil.sudoku.model.House;
import com.games.halogen.puzzlePencil.sudoku.model.PenMarks;

import java.util.ArrayList;

/*
A cell values are edited by parent grid.
Pencil marks are updated on cell value update
 */
public class Cell {
    private int dimensions;

    private Integer num;
    private ArrayList<House> houses;
    private IntPair coordinates;

    private PenMarks penMarks;
    private PenMarks visibleMarks;

    public Cell(IntPair coordinates, int dimensions) {
        this.coordinates = coordinates;
        this.dimensions = dimensions;

        setupPenMarks();

        houses = new ArrayList<>();
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
    Below functions should only be callable from Grid
     */
    void setValue(Integer num) {
        this.num = num;
    }

    void clearValue() {
        this.num = -1;
    }

    void addHouse(House h){
        houses.add(h);
    }

    public ArrayList<House> getHouses() {
        return houses;
    }

    public int getValue() {
        return num;
    }
}
