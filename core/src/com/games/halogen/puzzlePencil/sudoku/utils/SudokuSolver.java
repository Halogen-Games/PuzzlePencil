package com.games.halogen.puzzlePencil.sudoku.utils;

import com.games.halogen.puzzlePencil.sudoku.scene.view.cell.Cell;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.SudokuGrid;

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
 * -----lower level of X-Wing Techniques (chains of singles)------
 *  Example(2-chain): same digit twice only in Same unit, can remove this Digit from their other common units.
 *  3-chain??
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
            cell.setMiniums(SudokuUtils.getMiniums(grid, cell));

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

    /*If a cell has only one possible candidate, fill it*/
    private static boolean fillAllNakedSingles(SudokuGrid grid){
        return false;
    }

    /*Converts all hidden singles to naked singles*/
    private static boolean removeHiddenSingles(SudokuGrid grid) {
        return false;
    }

    /*Processes max difficulty, will be removed once all checks are added*/
    private static boolean processMaxDifficultyRule(SudokuGrid grid) {
        return false;
    }
}
