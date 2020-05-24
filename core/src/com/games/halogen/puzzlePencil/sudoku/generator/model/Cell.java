package com.games.halogen.puzzlePencil.sudoku.generator.model;

import com.games.halogen.gameEngine.utils.Pair;
import com.games.halogen.gameEngine.utils.Pair.IntPair;

import java.util.ArrayList;

/*
A cell values are edited by parent grid.
Pencil marks are updated on cell value update
 */
public class Cell {
    private int num;
    private ArrayList<House> houses;
    private IntPair coordinates;

    private PenMarks penMarks;
    private PenMarks visibleMarks;

    public PenMarks getPenMarks() {
        //todo fill
        return null;
    }

    public IntPair getCoordinates(){
        return this.coordinates;
    }
}
