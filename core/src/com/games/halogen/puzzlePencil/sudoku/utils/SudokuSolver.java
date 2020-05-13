package com.games.halogen.puzzlePencil.sudoku.utils;

import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.Miniums;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.SudokuGrid;
import com.games.halogen.puzzlePencil.sudoku.utils.SudokuUtils.UnitType;

import java.util.ArrayList;

/**
 * -----Scanning Techniques-----
 * level 1 - Naked Singles
 * level 2 - Hidden Singles
 *
 * -----Pair Removal Techniques-----
 * level 3 -  Naked Pairs'
 * level 4 - Hidden Pairs
 * Note: Can generalize to triples and quads (uninteresting?)
 *
 * -----lower level of X-Wing Techniques (2 length chain of singles)------
 *  Example(2-chain): same digit twice in a single unit, can remove this Digit from their other common units.
 *
 * -----X-Wing Like techniques (4 length chains of pairs)-----
 * General Technique :
 *      2 Singles in same unit(A), both pair up with another set of singles in units B and C.
 *      can remove all others of this single from A, B and C
 *      This is kind of a chaining with a chain of 4 cells. Cell 1 and 4 lie in A, Cell 2 in B and Cell 3 lies in C.
 * level 5 - A is a row or column, B and C are both either row or column
 * level 6 - A is a Block, B and C are same unit type
 * level 7 - A is any unit, B and C are diff Units types
 *
 * -----Longer chains than X-Wing ( 6 length chains)-----
 */
class SudokuSolver {
    //prevent object instantiation
    private SudokuSolver(){}

    /*
    Solves the grid up to a certain point only
     */
    static void solveGridCell(SudokuGrid grid, Cell cell) {
        SudokuUtils.findMiniums(grid);

        while(cell.isEmpty()) {
            //Level 1 - Naked Singles
            if (fillAllNakedSingles(grid)) {
                SudokuUtils.findMiniums(grid);
                continue;
            }else if (grid.getLevel() == 1) {
                break;
            }

            //Level 2 - Hidden Singles
            if (removeHiddenSingles(grid)) {
                continue;
            }else if (grid.getLevel() == 2) {
                break;
            }

            //Level 3 - Naked Pairs
            if (removeNakedPairs(grid)) {
                continue;
            }else if (grid.getLevel() == 3) {
                break;
            }
            break;
        }
    }

    /*
    If a cell has only one possible candidate, fill it
    */
    private static boolean fillAllNakedSingles(SudokuGrid grid){
        boolean isFilled = false;
        for(int i=0; i<grid.getNumRows(); i++){
            for(int j=0; j<grid.getNumRows(); j++){
                if(!grid.getCell(i,j).isEmpty()){
                    continue;
                }
                Miniums miniums = grid.getCell(i,j).getMiniums();
                if(miniums.size() == 1){
                    grid.setValue(i,j,miniums.get(0));
                    isFilled = true;
                }
            }
        }
        return isFilled;
    }

    /*
    Converts all hidden singles to naked singles
    */
    private static boolean removeHiddenSingles(SudokuGrid grid) {
        int numBlocks = grid.getNumBlocks();
        int numRows = grid.getNumRows();
        boolean isRemoved = false;

        //search all rows
        for(int i=0;i<numRows;i++){
            ArrayList<Cell> rowCells = SudokuUtils.getAllUnitCells(grid, i, 0, UnitType.ROW);
            while(removeHiddenSinglesInSet(rowCells, numRows)){
                isRemoved = true;
            }
        }

        //search all columns
        for(int i=0;i<numRows;i++){
            ArrayList<Cell> colCells = SudokuUtils.getAllUnitCells(grid, 0, i, UnitType.COLUMN);
            while(removeHiddenSinglesInSet(colCells, numRows)){
                isRemoved = true;
            }
        }

        //search all blocks
        for(int i=0;i<numRows;i++){
            int row = ((i/numBlocks)*numBlocks)%numRows;
            int column = (i*numBlocks)%numRows;
            ArrayList<Cell> blockCells = SudokuUtils.getAllUnitCells(grid, row, column, UnitType.BLOCK);
            while(removeHiddenSinglesInSet(blockCells, numRows)){
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    //Remove hidden singles in a given cell set
    private static boolean removeHiddenSinglesInSet(ArrayList<Cell> cells, int numRows){
        boolean isRemoved = false;

        for(int i=1; i<=numRows; i++){
            Cell foundCell = null;
            int count = 0;
            for(Cell c:cells){
                if(c.getMiniums().hasNum(i)){
                    foundCell = c;
                    count++;
                }
            }
            if(count == 1){
                if(foundCell.getMiniums().size() == 1){
                    //this is a naked single, not a hidden single
                    return false;
                }
                foundCell.getMiniums().clearAllMiniums();
                foundCell.getMiniums().add(i);
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    private static boolean removeNakedPairs(SudokuGrid grid) {
        return false;
    }

    /*Processes max difficulty, will be removed once all checks are added*/
    private static boolean processMaxDifficultyRule(SudokuGrid grid) {
        return false;
    }
}
