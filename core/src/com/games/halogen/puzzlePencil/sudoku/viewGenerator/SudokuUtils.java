package com.games.halogen.puzzlePencil.sudoku.viewGenerator;

import com.games.halogen.gameEngine.utils.Pair.IntPair;
import com.games.halogen.puzzlePencil.sudoku.generator.model.HouseType;
import com.games.halogen.puzzlePencil.sudoku.view.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.view.grid.SudokuGrid;

import java.util.ArrayList;

public class SudokuUtils {
    //prevent instantiation
    private SudokuUtils(){}

    static ArrayList<Cell> getCommonUnitCells(SudokuGrid grid, ArrayList<Cell> cellsWithI) {
        if(cellsWithI.size()<2){
            throw new RuntimeException("Need at least 2 elements to find common");
        }

        ArrayList<Cell> rv = new ArrayList<>();

        int r = cellsWithI.get(0).getRow();
        int c = cellsWithI.get(0).getColumn();
        IntPair b = new IntPair(r/grid.getNumBlocks(), c/grid.getNumBlocks());

        boolean rowCommon = true;
        boolean colCommon = true;
        boolean blockCommon = true;

        //check if row is common
        for(int i=1;i<cellsWithI.size();i++){
            if(cellsWithI.get(i).getRow() != r){
                rowCommon = false;
                break;
            }
        }

        //check if col is common
        for(int i=1;i<cellsWithI.size();i++){
            if(cellsWithI.get(i).getColumn() != c){
                colCommon = false;
                break;
            }
        }

        //check if block is common
        for(int i=1;i<cellsWithI.size();i++){
            IntPair tempB = new IntPair(cellsWithI.get(i).getRow()/grid.getNumBlocks(), cellsWithI.get(i).getColumn()/grid.getNumBlocks());
            if(!b.getFirst().equals(tempB.getFirst()) || !b.getSecond().equals(tempB.getSecond())){
                blockCommon = false;
                break;
            }
        }

        if(rowCommon){
            rv.addAll(getEmptyUnitCells(grid,r, c, HouseType.ROW));
        }

        if(colCommon){
            rv.addAll(getEmptyUnitCells(grid,r, c, HouseType.COLUMN));
        }

        if(blockCommon){
            rv.addAll(getEmptyUnitCells(grid,r, c, HouseType.BLOCK));
        }


        return rv;
    }


    /*
    Finds miniums for all cells
     */

    public static void findMiniums(SudokuGrid grid) {
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
        if(!cell.isEmpty()){
            return;
        }
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

        for(HouseType u: HouseType.values()) {
            for (Cell c : getAllUnitCells(grid, cell.getRow(), cell.getColumn(), u)) {
                if (!c.isEmpty()) {
                    ints.remove((Integer)c.getValue());
                }
            }
        }

        return ints;
    }

    /*
    Get empty cells of a specific type of unit in which the given cell belongs
    */
    static ArrayList<Cell> getEmptyUnitCells(SudokuGrid grid, int row, int column, HouseType unitType){
        ArrayList<Cell> rv = getAllUnitCells(grid, row, column, unitType);
        for(int i=0; i<rv.size(); i++){
            if (!rv.get(i).isEmpty()){
                rv.remove(i);
                i--;
            }
        }

        return rv;
    }

    /*
    Get cells of a specific type of unit in which the given cell belongs
    */
    private static ArrayList<Cell> getAllUnitCells(SudokuGrid grid, int row, int column, HouseType unitType){
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
        for(HouseType u: HouseType.values()){
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
