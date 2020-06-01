package com.games.halogen.puzzlePencil.sudoku.grid.model.grid;

import com.games.halogen.gameEngine.utils.Pair.IntPair;
import com.games.halogen.puzzlePencil.sudoku.grid.model.House;
import com.games.halogen.puzzlePencil.sudoku.grid.model.PenMarks;

import java.util.ArrayList;

/*
A cell values are edited by parent grid.
Pencil marks are updated on cell value update
 */
public class Cell {
    private int dimensions;

    private static final int EMPTY_VALUE = -1;
    private Integer num;
    private boolean isEditable;
    private boolean isValidVal;

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

        for (int i = 0; i < dimensions; i++) {
            penMarks.addMark(i + 1);
        }
    }

    /*
    Below functions should only be callable from Grid
     */
    void setValue(Integer num) {
        this.num = num;
    }

    void clearValue() {
        this.num = EMPTY_VALUE;
    }

    void addHouse(House h) {
        houses.add(h);
    }

    void setEditable(boolean editable){
        this.isEditable = editable;
    }

    void setValidity(boolean validity){
        this.isValidVal = validity;
    }

    /*
    Below functions are public and should be getters only
     */
    public PenMarks getPenMarks() {
        return penMarks;
    }

    public PenMarks getVisibleMarks() {
        return visibleMarks;
    }

    public IntPair getCoordinates() {
        return this.coordinates;
    }

    public ArrayList<House> getHouses() {
        return houses;
    }

    public int getValue() {
        return num;
    }

    public boolean hasValue(){
        return this.getValue() != EMPTY_VALUE;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public boolean hasValidValue() {
        return isValidVal;
    }
}
