package com.games.halogen.puzzlePencil.sudoku.utils;

import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.Miniums;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.SudokuGrid;

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
        boolean fillPossible = true;

        while(cell.isEmpty() && fillPossible) {
            fillPossible = false;
            SudokuUtils.findMiniums(grid);

            //Level 1 - Naked Singles
            if (fillAllNakedSingles(grid)) {
                fillPossible = true;
                continue;
            }

            if (grid.getLevel() == 1) {
                continue;
            }

            //Level 2 - Hidden Singles
            if (removeHiddenSingles(grid)) {
                System.out.println("Removing Hidden Single");
                fillPossible = true;
                continue;
            }

            if (grid.getLevel() == 2) {
                continue;
            }

            if(processMaxDifficultyRule(grid)){
                fillPossible = true;
            }
        }
    }

    /*
    If a cell has only one possible candidate, fill it
    */
    private static boolean fillAllNakedSingles(SudokuGrid grid){
        for(int i=0; i<grid.getNumRows(); i++){
            for(int j=0; j<grid.getNumRows(); j++){
                Miniums miniums = grid.getCell(i,j).getMiniums();
                if(miniums.size() == 1){
                    grid.setValue(i,j,miniums.get(0));
                }
            }
        }
        return false;
    }

    /*
    Converts all hidden singles to naked singles
    */
    private static boolean removeHiddenSingles(SudokuGrid grid) {
        int numRows = grid.getNumRows();
        boolean isRemoved = false;

        //search all rows
        for(int i=0;i<numRows;i++){
            while(removeHiddenSinglesInSet(SudokuUtils.getAllUnitCells(grid, i, 0, SudokuUtils.UnitType.ROW), numRows)){
                isRemoved = true;
            }
        }

        for(int i=0;i<numRows;i++){
            while(removeHiddenSinglesInSet(SudokuUtils.getAllUnitCells(grid, 0, i, SudokuUtils.UnitType.COLUMN), numRows)){
                isRemoved = true;
            }
        }

        for(int i=0;i<numRows;i++){
            while(removeHiddenSinglesInSet(SudokuUtils.getAllUnitCells(grid, ((i/3)*3)%9, (i*3)%9, SudokuUtils.UnitType.BLOCK), numRows)){
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
                foundCell.getMiniums().clearAllMiniums();
                foundCell.getMiniums().add(i);
            }
        }

        return isRemoved;
    }

    /*Processes max difficulty, will be removed once all checks are added*/
    private static boolean processMaxDifficultyRule(SudokuGrid grid) {
        return false;
    }
}
