package com.games.halogen.puzzlePencil.sudoku.utils;

import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.Miniums;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.Collections;

public class SudokuUtils {


    public enum UnitType{
        ROW,
        COLUMN,
        BLOCK;
    }
    static ArrayList<Cell> getRandomizedCellsArray(SudokuGrid grid){
        int blockSize = grid.getBlockSize();
        int gridSize = blockSize*blockSize;

        ArrayList<Cell> rv = new ArrayList<>();

        for(int i=0; i< gridSize; i++){
            for(int j=0; j<gridSize; j++){
                rv.add(grid.getCell(i,j));
            }
        }

        Collections.shuffle(rv);
        return rv;
    }

    /*
    Finds miniums for all cells
     */

    static void findMiniums(SudokuGrid grid) {
        for(int i=0; i<grid.getNumRows(); i++){
            for(int j=0; j<grid.getNumRows(); j++){
                findMiniumsForCells(grid, grid.getCell(i,j));
            }
        }
    }
    /*
    Returns the miniums of current cell according to given grid
    */

    private static void findMiniumsForCells(SudokuGrid grid, Cell cell) {
        Miniums miniums = cell.getMiniums();
        miniums.fillAll(grid.getNumRows());

        //check all the three units one by one
        for(UnitType u:UnitType.values()){
            for (Cell c:getUnitCells(grid, cell, u)) {
                if (!c.isEmpty()) {
                    miniums.remove(c.getValue());
                }
            }
        }

        cell.setMiniums(miniums);
    }
    /*
    Get cells of a specific type of unit in which the given cell belongs
    */

    private static ArrayList<Cell> getUnitCells(SudokuGrid grid, Cell cell, UnitType unitType){
        ArrayList<Cell> rv = new ArrayList<>();

        int gridSize = grid.getNumRows();
        int blockSize = (int) Math.sqrt(gridSize);

        int cellRow = cell.getCoordinates().getFirst();
        int cellCol = cell.getCoordinates().getSecond();

        int blockRowOffset = (cellRow / blockSize) * blockSize;
        int blockColOffset = (cellCol / blockSize) * blockSize;

        switch(unitType){
            case ROW:
                for (int col = 0; col < grid.getNumRows(); col++) {
                    rv.add(grid.getCell(cellRow,col));
                }
                break;

            case COLUMN:
                for (int row = 0; row < grid.getNumRows(); row++) {
                    rv.add(grid.getCell(row,cellCol));
                }
                break;

            case BLOCK:
                for (int row = 0; row < blockSize; row++) {
                    for (int col = 0; col < blockSize; col++) {
                        rv.add(grid.getCell(row + blockRowOffset, col + blockColOffset));
                    }
                }
                break;
        }

        return rv;
    }

    /*
    * Updates the validity status of all cells if the given cell is changed
     */
    public static void updateCellValidityOnUpdate(SudokuGrid grid, Cell cell) {
        int cellVal = cell.getValue();

        //check all the three units one by one
        boolean isValid = true;
        for(UnitType u:UnitType.values()){
            for (Cell c:getUnitCells(grid, cell, u)) {
                if (!c.isEmpty()) {
                    if(c != cell && c.getValue() == cellVal && c.getValue() != -1){
                        c.setValidity(false);
                        isValid = false;
                    }else{
                        c.setValidity(true);
                    }
                }
            }
        }

        cell.setValidity(isValid);
    }
}
