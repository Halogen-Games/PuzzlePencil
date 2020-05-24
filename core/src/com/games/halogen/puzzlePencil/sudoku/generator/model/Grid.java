package com.games.halogen.puzzlePencil.sudoku.generator.model;

import java.util.ArrayList;

/*
When cells are filled, pen marks of other cells are updated
 */
public class Grid {
    public int getDimension() {
        return 0;
    }

    public ArrayList<House> getHouses(HouseType houseType) {
        //todo fill
        return new ArrayList<>();
    }

    /*
    Calculates and returns penMarks for given cell
     */
    public PenMarks getPenMarks(int i, int j) {
        //todo fill
        return new PenMarks();
    }

    public void setCellValue(Integer first, Integer second, Integer integer) {
        //todo fill
    }

    public void clearCellValue(Integer first, Integer second) {
        //todo fill
    }

    public void clearAllCells() {
        //todo fill
    }
}
