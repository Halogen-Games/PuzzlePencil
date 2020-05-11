package com.games.halogen.puzzlePencil.sudoku.utils;

import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.Collections;

public class SudokuGenerator {
    //prevent instantiation
    private SudokuGenerator(){}

    public static void generate(SudokuGrid grid) {
        generateSolvedGrid(grid);
        randomizeGrid(grid);
        randomlyRemoveNumbers(grid);
    }

    private static void generateSolvedGrid(SudokuGrid grid){
        //fill first row of blocks by shifting linear list by blockSize
        int blockSize = grid.getBlockSize();
        int gridSize = blockSize * blockSize;

        for(int i=0; i<blockSize; i++){
            for(int j=0;j<blockSize;j++) {
                int startPos = i + j*blockSize;
                for (int k = 0; k < gridSize; k++) {
                    int value = (k + startPos) % gridSize + 1;
                    grid.setValue(i*blockSize + j, k, value);
                }
            }
        }
    }

    private static void randomizeGrid(SudokuGrid grid) {
        //Randomize numbers
        ArrayList<Integer> numberMap = new ArrayList<>();
        int blockSize = grid.getBlockSize();
        int gridSize = blockSize*blockSize;

        for(int i=0;i<blockSize*blockSize;i++){
            numberMap.add(i+1);
        }
        Collections.shuffle(numberMap);

        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                grid.setValue(i,j,numberMap.get(grid.getValue(i,j)-1));
            }
        }

        //todo: fill to randomize by swapping numbers, rows and cols within blocks and rows and cols of blocks
    }



    private static void randomlyRemoveNumbers(SudokuGrid cells) {
        //randomly select removal order
        ArrayList<Cell> randomizedCells = SudokuUtils.getRandomizedCellsArray(cells);

        for(int i=0; i<randomizedCells.size(); i++) {
            removeNumberInCell(cells, randomizedCells.get(i));
        }
    }

    //todo: give better name
    private static void removeNumberInCell(SudokuGrid grid, Cell cell) {
        int val = cell.getValue();

        grid.saveState();
        cell.setEmpty();
        SudokuSolver.solveGridCell(grid, cell);

        int newVal = cell.getValue();

        grid.loadState();
        if(newVal == val){
            cell.setEmpty();
        }
    }
}
