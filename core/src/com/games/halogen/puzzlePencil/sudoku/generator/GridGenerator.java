package com.games.halogen.puzzlePencil.sudoku.generator;

import com.games.halogen.gameEngine.utils.Pair.IntPair;
import com.games.halogen.puzzlePencil.sudoku.grid.model.grid.Grid;
import com.games.halogen.puzzlePencil.sudoku.grid.model.PenMarks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GridGenerator {
    private static final Random random = GridGeneratorParameters.getRandom();

    public static void generatePuzzle(Grid grid){
        grid.resetGrid();
        if(!generateFilledGrid(grid)){
            throw new RuntimeException("Can't create a filled grid. Possible Bug");
        }

        //fixme: this should cause red mark
        grid.setCellValue(1,1,1);

        grid.freezeFilledCells();
    }

    /*
    Creates a completely filled valid grid
     */
    private static boolean generateFilledGrid(Grid grid) {
        ArrayList<Integer> validNums = new ArrayList<>();
        for(int i=1; i<=grid.getDimension(); i++){
            validNums.add(i);
        }

        Collections.shuffle(validNums, random);
        return fillRecursively(grid, 0, validNums);
    }

    //fills the given cell of the grid with the first number from validFills
    private static boolean fillRecursively(Grid grid, int cellNum, ArrayList<Integer> validFills) {
        //base case 1 - return failure if no possible value to fill
        if(validFills.size() == 0){
            return false;
        }

        IntPair currCoords = getCellCoordinatesFromIndex(cellNum, grid.getDimension());
        for(int i=0; i<validFills.size(); i++){
            grid.setCellValue(currCoords.getFirst(), currCoords.getSecond(), validFills.get(i));

            //base case 2 - if grid completely filled, return success
            if(cellNum == grid.getDimension() * grid.getDimension() - 1){
                return true;
            }

            //fill next cell
            IntPair nextCellCoords = getCellCoordinatesFromIndex(cellNum + 1, grid.getDimension());
            if(fillRecursively(grid, cellNum + 1, getValidFills(grid, nextCellCoords))){
                return true;
            }
        }

        grid.clearCellValue(currCoords.getFirst(), currCoords.getSecond());
        return false;
    }

    //get coordinates of a cell from its index. Cells are indexed from 1 to n*n row wise
    private static IntPair getCellCoordinatesFromIndex(int cellNum, int gridDimensions){
        if(cellNum >= gridDimensions * gridDimensions){
            throw new RuntimeException("Cell num out of bounds : " + cellNum);
        }

        return new IntPair(cellNum / gridDimensions, cellNum % gridDimensions);
    }

    //get valid numbers that can be filled in the grid at specific coordinates
    private static ArrayList<Integer> getValidFills(Grid grid, IntPair coordinates){
        PenMarks pm = grid.getCell(coordinates.getFirst(), coordinates.getSecond()).getPenMarks();
        ArrayList<Integer> ints = new ArrayList<>();
        for(int i = 0; i<pm.getMarksCount(); i++){
            ints.add(pm.getMark(i));
        }
        Collections.shuffle(ints, random);

        return ints;
    }
}
