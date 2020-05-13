package com.games.halogen.puzzlePencil.sudoku.utils;

import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.SudokuGrid;

import java.util.ArrayList;

public class SudokuUtils {
    //prevent instantiation
    private SudokuUtils(){}

    public enum UnitType{
        ROW,
        COLUMN,
        BLOCK
    }


    /*
    Finds miniums for all cells
     */

    static void findMiniums(SudokuGrid grid) {
        for(int i=0; i<grid.getNumRows(); i++){
            for(int j=0; j<grid.getNumRows(); j++){
                findMiniumsForCell(grid, grid.getCell(i,j));
            }
        }
    }

    /*
    Returns the miniums of current cell according to given grid
    */
    private static void findMiniumsForCell(SudokuGrid grid, Cell cell) {
        cell.getMiniums().clearAllMiniums();
        ArrayList<Integer> validVals = getValidCandidates(grid, cell);

        for(int i=0; i<validVals.size(); i++){
            cell.getMiniums().add(validVals.get(i));
        }
    }

    /*
    Return possible candidates that can be filed in given cell
     */
    static ArrayList<Integer> getValidCandidates(SudokuGrid grid, Cell cell){
        ArrayList<Integer> ints = new ArrayList<>();

        for(int i=1; i<=grid.getNumRows(); i++){
            ints.add(i);
        }

        for(UnitType u:UnitType.values()) {
            for (Cell c : getAllUnitCells(grid, cell.getRow(), cell.getColumn(), u)) {
                if (!c.isEmpty()) {
                    ints.remove((Integer)c.getValue());
                }
            }
        }

        return ints;
    }

    /*
    Get cells of a specific type of unit in which the given cell belongs
    */
    static ArrayList<Cell> getAllUnitCells(SudokuGrid grid, int row, int column, UnitType unitType){
        ArrayList<Cell> rv = new ArrayList<>();

        int gridSize = grid.getNumRows();
        int blockSize = (int) Math.sqrt(gridSize);

        int blockRowOffset = (row / blockSize) * blockSize;
        int blockColOffset = (column / blockSize) * blockSize;

        switch(unitType){
            case ROW:
                for (int c = 0; c < grid.getNumRows(); c++) {
                    rv.add(grid.getCell(row,c));
                }
                break;

            case COLUMN:
                for (int r = 0; r < grid.getNumRows(); r++) {
                    rv.add(grid.getCell(r,column));
                }
                break;

            case BLOCK:
                for (int r = 0; r < blockSize; r++) {
                    for (int c = 0; c < blockSize; c++) {
                        rv.add(grid.getCell(r + blockRowOffset, c + blockColOffset));
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
            for (Cell c: getAllUnitCells(grid, cell.getRow(), cell.getColumn(), u)) {
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
