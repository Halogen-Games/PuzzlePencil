package com.games.halogen.puzzlePencil.oldSudoku.viewGenerator;

import com.games.halogen.puzzlePencil.oldSudoku.viewOld.grid.Cell;
import com.games.halogen.puzzlePencil.oldSudoku.viewOld.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SudokuGenerator {
    public static boolean highestLevelUsed;
    private static Random random;

    //prevent instantiation
    private SudokuGenerator(){}

    public static void generate(SudokuGrid grid) {
        highestLevelUsed = false;
        for(int randSeed = 13; randSeed<100000; randSeed++){
            random = new Random(randSeed);
            System.out.println("trying generation at : " + randSeed);
            grid.clearAllCells();
            if (!generateFilledGrid(grid)) {
                throw new RuntimeException("unable to fill grid, possible algo error");
            }

            randomlyRemoveNumbers(grid);

            SudokuUtils.findMiniums(grid);

            if(highestLevelUsed){
                System.out.println("generated at seed : " + randSeed);
                break;
            }
        }

        grid.clearAllMiniums();
    }

    private static boolean generateFilledGrid(SudokuGrid grid) {
        ArrayList<Integer> validNums = new ArrayList<>();
        for(int i=1; i<=grid.getNumRows(); i++){
            validNums.add(i);
        }

        Collections.shuffle(validNums, random);
        return fillRecursively(grid, 0, validNums);
    }

    /*
    fills the given cell of the grid with the first number from validNums
     */
    private static boolean fillRecursively(SudokuGrid grid, int cellNum, ArrayList<Integer> validNums) {
        //base case 1 - return failure if no possible value to fill
        if(validNums.size() == 0){
            return false;
        }

        Cell currCell = getCellFromIndex(grid, cellNum);
        for(int i=0; i<validNums.size(); i++){
            currCell.setValue(validNums.get(i));

            //base case 2 - if grid completely filled, return success
            if(cellNum == grid.getNumRows() * grid.getNumRows() - 1){
                return true;
            }

            //fill next cell
            Cell nextCell = getCellFromIndex(grid, cellNum + 1);
            if(fillRecursively(grid, cellNum + 1,getShuffledValidCandidates(grid, nextCell))){
                return true;
            }
        }

        currCell.setEmpty();
        return false;
    }

    /*
    randomly starts removing cells from a complete grid to form a puzzle
    numbers are only removed if they can be filled back uniquely
     */
    private static void randomlyRemoveNumbers(SudokuGrid cells) {
        ArrayList<Cell> randomizedCells = getShuffledCellsArray(cells);

        for(int i=0; i<randomizedCells.size(); i++) {
            removeNumberInCell(cells, randomizedCells.get(i));
        }
    }

    //todo: give better name
    private static void removeNumberInCell(SudokuGrid grid, Cell cell) {
        int val = cell.getValue();

        grid.saveState();
        cell.setEmpty();
        SudokuSolver.solveGridCell(grid, cell, grid.getLevel());

        int newVal = cell.getValue();

        grid.loadState();
        if(newVal == val){
            cell.setEmpty();
        }
    }

    private static ArrayList<Cell> getShuffledCellsArray(SudokuGrid grid){
        int blockSize = grid.getNumBlocks();
        int gridSize = blockSize*blockSize;

        ArrayList<Cell> rv = new ArrayList<>();

        for(int i=0; i< gridSize; i++){
            for(int j=0; j<gridSize; j++){
                rv.add(grid.getCell(i,j));
            }
        }

        Collections.shuffle(rv,random);
        return rv;
    }

    private static Cell getCellFromIndex(SudokuGrid grid, int cellNum){
        int numRows = grid.getNumRows();
        if(cellNum >= numRows * numRows){
            throw new RuntimeException("Cell num out of bounds : " + cellNum);
        }

        return grid.getCell(cellNum / grid.getNumRows(), cellNum % grid.getNumRows());
    }

    private static ArrayList<Integer> getShuffledValidCandidates(SudokuGrid grid, Cell cell){
        ArrayList<Integer> ints = SudokuUtils.getValidCandidates(grid, cell);
        Collections.shuffle(ints, random);
        return ints;
    }
}
