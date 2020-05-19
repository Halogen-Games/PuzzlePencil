package com.games.halogen.puzzlePencil.sudoku.viewGenerator;

import com.games.halogen.puzzlePencil.sudoku.generator.model.HouseType;
import com.games.halogen.puzzlePencil.sudoku.view.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.view.grid.Miniums;
import com.games.halogen.puzzlePencil.sudoku.view.grid.SudokuGrid;

import java.util.ArrayList;

/**
 * level 1 - Naked Singles
 * level 2 - Hidden Singles
 * level 3 -  Naked Pairs
 * level 4 - Singles Group: same digit twice, thrice in a single unit, can remove this Digit from their other common units.
 * level 5 - Hidden Pairs
 *
 * ------------X-Wing---------------
 * General Technique : (X,Y) -> A and Y share a unit
 * A minium present only twice in units A and B in cells (P,Q) and (R,S)
 * If (P,R) and (Q,S) || (P,S) and (Q,R), can remove this minium from these new common units
 *
 * level 5 - A is a row or column, B and C are both either row or column
 * level 6 - A is a Block, B and C are same unit types
 * level 7 - A is any unit, B and C are diff Units types
 */

/*
todo: too much repetitive code, make better
 */
class SudokuSolver {
    //prevent object instantiation
    private SudokuSolver(){
    }

    /*
    Solves the grid completely up to given level
     */
    static void solveGrid(SudokuGrid grid, int level){
        //todo: fill
        solveGridCell(grid, null, level);
    }

    /*
    Solves the grid until the given cell is filled
     */
    static void solveGridCell(SudokuGrid grid, Cell cell, int level) {
        SudokuUtils.findMiniums(grid);

        boolean highestLevelUsed = false;

        boolean loop = true;
        while(loop) {
            if(cell == null){
                loop = !grid.isFilled();
            }else{
                loop = cell.isEmpty();
            }

            //Level 1 - Naked Singles
            if (fillNakedSingles(grid)) {
                SudokuUtils.findMiniums(grid);
                if (level == 1) {
                    highestLevelUsed = true;
                }
                continue;
            }else if (level == 1) {
                break;
            }

            //Level 2 - Hidden Singles
            if (removeHiddenSingles(grid)) {
                if (level == 2) {
                    highestLevelUsed = true;
                }
                continue;
            }else if (level == 2) {
                break;
            }

            //Level 3 - Naked Pairs
            if (removeNakedPairs(grid)) {
                if (level == 3) {
                    highestLevelUsed = true;
                }
                continue;
            }else if (level == 3) {
                break;
            }

            //Level 4 - Chained Singles
            if (removeChainedSingles(grid)) {
                if (level == 4) {
                    highestLevelUsed = true;
                }
                continue;
            }else if (level == 4) {
                break;
            }

            //Level 5 - Hidden Pairs
            if (removeHiddenPairs(grid)) {
                if (level == 5) {
                    highestLevelUsed = true;
                }
                continue;
            }else if (level == 5) {
                break;
            }

            break;
        }

        if(highestLevelUsed && cell!= null && !cell.isEmpty()){
            SudokuGenerator.highestLevelUsed = true;
        }
    }

    /*
    If a cell has only one possible candidate, fill it
    */
    private static boolean fillNakedSingles(SudokuGrid grid){
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
            ArrayList<Cell> rowCells = SudokuUtils.getEmptyUnitCells(grid, i, 0, HouseType.ROW);
            while(removeHiddenSinglesInSet(rowCells, numRows)){
                isRemoved = true;
            }
        }

        //search all columns
        for(int i=0;i<numRows;i++){
            ArrayList<Cell> colCells = SudokuUtils.getEmptyUnitCells(grid, 0, i, HouseType.COLUMN);
            while(removeHiddenSinglesInSet(colCells, numRows)){
                isRemoved = true;
            }
        }

        //search all blocks
        for(int i=0;i<numRows;i++){
            int row = ((i/numBlocks)*numBlocks)%numRows;
            int column = (i*numBlocks)%numRows;
            ArrayList<Cell> blockCells = SudokuUtils.getEmptyUnitCells(grid, row, column, HouseType.BLOCK);
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

            //find number of cells with i in it
            int count = 0;
            for(Cell c:cells){
                if(c.getMiniums().hasNum(i)){
                    foundCell = c;
                    count++;
                }
            }

            //if only one cell with i in it, and that cell has other miniums, it is a hidden single
            if(count == 1 && foundCell.getMiniums().size()>1){
                foundCell.getMiniums().clearAllMiniums();
                foundCell.getMiniums().add(i);
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    /*
    Removes all naked pairs
     */
    private static boolean removeNakedPairs(SudokuGrid grid) {
        int numBlocks = grid.getNumBlocks();
        int numRows = grid.getNumRows();
        boolean isRemoved = false;

        //search all rows
        for(int i=0;i<numRows;i++){
            ArrayList<Cell> rowCells = SudokuUtils.getEmptyUnitCells(grid, i, 0, HouseType.ROW);
            while(removeNakedPairsInSet(rowCells, numRows)){
                isRemoved = true;
            }
        }

        //search all columns
        for(int i=0;i<numRows;i++){
            ArrayList<Cell> colCells = SudokuUtils.getEmptyUnitCells(grid, 0, i, HouseType.COLUMN);
            while(removeNakedPairsInSet(colCells, numRows)){
                isRemoved = true;
            }
        }

        //search all blocks
        for(int i=0;i<numRows;i++){
            int row = ((i/numBlocks)*numBlocks)%numRows;
            int column = (i*numBlocks)%numRows;
            ArrayList<Cell> blockCells = SudokuUtils.getEmptyUnitCells(grid, row, column, HouseType.BLOCK);
            while(removeNakedPairsInSet(blockCells, numRows)){
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    //remove naked pairs in a given cell set
    private static boolean removeNakedPairsInSet(ArrayList<Cell> cells, int numRows) {
        boolean isRemoved = false;

        for(int i=1; i<=numRows; i++){
            for(int j=i+1; j<=numRows; j++) {
                int count = 0;
                for (Cell c : cells) {
                    //find cells with only i and j
                    if (c.getMiniums().hasNum(i) && c.getMiniums().hasNum(j) && c.getMiniums().size() == 2) {
                        count++;
                    }
                }
                if(count > 2){
                    throw new RuntimeException("Impossible case of naked pair. Solver/generator is buggy");
                }
                if (count == 2) {
                    //remove all except i,j from all cells that have i or j and some other other item as well
                    for(Cell c : cells){
                        boolean p = c.getMiniums().hasNum(i);
                        boolean q = c.getMiniums().hasNum(j);
                        boolean r = c.getMiniums().size() == 2;
                        if (p ^ q || (p && !r)) {
                            c.getMiniums().remove(i);
                            c.getMiniums().remove(j);
                            isRemoved = true;
                        }
                    }
                }
            }
        }

        return isRemoved;
    }

    private static boolean removeChainedSingles(SudokuGrid grid) {
        int numBlocks = grid.getNumBlocks();
        int numRows = grid.getNumRows();
        boolean isRemoved = false;

        //search all rows
        for(int i=0;i<numRows;i++){
            ArrayList<Cell> rowCells = SudokuUtils.getEmptyUnitCells(grid, i, 0, HouseType.ROW);
            while(removeChainedSinglesInSet(grid, rowCells, numRows)){
                isRemoved = true;
            }
        }

        //search all columns
        for(int i=0;i<numRows;i++){
            ArrayList<Cell> colCells = SudokuUtils.getEmptyUnitCells(grid, 0, i, HouseType.COLUMN);
            while(removeChainedSinglesInSet(grid, colCells, numRows)){
                isRemoved = true;
            }
        }

        //search all blocks
        for(int i=0;i<numRows;i++){
            int row = ((i/numBlocks)*numBlocks)%numRows;
            int column = (i*numBlocks)%numRows;
            ArrayList<Cell> blockCells = SudokuUtils.getEmptyUnitCells(grid, row, column, HouseType.BLOCK);
            while(removeChainedSinglesInSet(grid, blockCells, numRows)){
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    private static boolean removeChainedSinglesInSet(SudokuGrid grid, ArrayList<Cell> cells, int numRows) {
        boolean isRemoved = false;
        for(int i=1; i<=numRows;i++){
            ArrayList<Cell> cellsWithI = new ArrayList<>();

            for(Cell c: cells){
                if(c.getMiniums().hasNum(i)){
                    cellsWithI.add(c);
                }
            }

            if(cellsWithI.size() < 2 || cellsWithI.size() > 3){
                continue;
            }

            ArrayList<Cell> commonUnitCells = SudokuUtils.getCommonUnitCells(grid, cellsWithI);
            int countRemoved = 0;
            for(Cell c:commonUnitCells){
                if(c.getMiniums().hasNum(i)) {
                    c.getMiniums().remove(i);
                    countRemoved++;
                }
            }

            for(Cell c:cellsWithI){
                c.getMiniums().add(i);
                countRemoved--;
            }

            if(countRemoved > 0){
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    private static boolean removeHiddenPairs(SudokuGrid grid) {
        int numBlocks = grid.getNumBlocks();
        int numRows = grid.getNumRows();
        boolean isRemoved = false;

        //search all rows
        for(int i=0;i<numRows;i++){
            ArrayList<Cell> rowCells = SudokuUtils.getEmptyUnitCells(grid, i, 0, HouseType.ROW);
            while(removeHiddenPairsInSet(rowCells, numRows)){
                isRemoved = true;
            }
        }

        //search all columns
        for(int i=0;i<numRows;i++){
            ArrayList<Cell> colCells = SudokuUtils.getEmptyUnitCells(grid, 0, i, HouseType.COLUMN);
            while(removeHiddenPairsInSet(colCells, numRows)){
                isRemoved = true;
            }
        }

        //search all blocks
        for(int i=0;i<numRows;i++){
            int row = ((i/numBlocks)*numBlocks)%numRows;
            int column = (i*numBlocks)%numRows;
            ArrayList<Cell> blockCells = SudokuUtils.getEmptyUnitCells(grid, row, column, HouseType.BLOCK);
            while(removeHiddenPairsInSet(blockCells, numRows)){
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    private static boolean removeHiddenPairsInSet(ArrayList<Cell> cells, int numRows) {
        boolean isRemoved = false;

        for(int i=1; i<=numRows; i++){
            for(int j=i+1; j<=numRows; j++) {
                ArrayList<Cell> validCells = new ArrayList<>();
                for (Cell c : cells) {
                    //find cells with i and j and more than 2 miniums
                    if (c.getMiniums().hasNum(i) && c.getMiniums().hasNum(j)) {
                        validCells.add(c);
                    }
                }
                if (validCells.size() == 2 && (validCells.get(0).getMiniums().size() > 2 || validCells.get(1).getMiniums().size() > 2)) {
                    //remove all except i,j from c1 and c2
                    Cell c1 = validCells.get(0);
                    Cell c2 = validCells.get(1);

                    c1.getMiniums().clearAllMiniums();
                    c1.getMiniums().add(i);
                    c1.getMiniums().add(j);

                    c2.getMiniums().clearAllMiniums();
                    c2.getMiniums().add(i);
                    c2.getMiniums().add(j);

                    isRemoved = true;
                }
            }
        }

        return isRemoved;
    }

    /*Processes max difficulty, will be removed once all checks are added*/
    private static boolean processMaxDifficultyRule(SudokuGrid grid) {
        return false;
    }
}
