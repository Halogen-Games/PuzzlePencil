package com.games.halogen.puzzlePencil.sudoku.utils;

import com.badlogic.gdx.math.Vector2;
import com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;

public class SudokuGenerator {
    private int gridSize;
    private int blockSize;

    private int level;

    private SudokuSolver solver;

    public void generate(ArrayList<ArrayList<Cell>> cells, int level) {
        gridSize = cells.size();
        double numBlocks = Math.sqrt(gridSize);
        if((int)numBlocks != numBlocks){
            throw new RuntimeException("grid size is not a prefect square");
        }

        this.blockSize = (int)numBlocks;

        this.level = level;

        this.solver = new SudokuSolver();

        generateSolvedGrid(cells);
        randomizeGrid(cells);
        randomlyRemoveNumbers(cells);
    }

    private void generateSolvedGrid(ArrayList<ArrayList<Cell>> cells){
        //fill first row of blocks by shifting linear list by blockSize
        for(int i=0; i<blockSize; i++){
            for(int j=0;j<blockSize;j++) {
                int startPos = i + j*blockSize;
                for (int k = 0; k < gridSize; k++) {
                    int value = (k + startPos) % gridSize + 1;
                    cells.get(i*blockSize + j).get(k).setValue(value);
                }
            }
        }
    }

    private void randomizeGrid(ArrayList<ArrayList<Cell>> cells) {
        //todo: fill to randomize by swapping numbers, rows and cols within blocks and rows and cols of blocks
        ArrayList<Integer> randomizedNums = new ArrayList<>();
        for(int i=0;i<blockSize*blockSize;i++){
            randomizedNums.add(i+1);
        }
        Collections.shuffle(randomizedNums);

        for(int i=0;i<cells.size();i++){
            for(int j=0;j<cells.get(0).size();j++){
                cells.get(i).get(j).setValue(randomizedNums.get(cells.get(i).get(j).getValue()-1));
            }
        }
    }

    private void randomlyRemoveNumbers(ArrayList<ArrayList<Cell>> cells) {
        //randomly select removal order
        ArrayList<Cell> randomizedCells = getRandomizedCellsArray(cells);

        for(int i=0; i<randomizedCells.size(); i++) {
            removeNumberInCell(cells, randomizedCells.get(i));
        }
    }

    private ArrayList<Cell> getRandomizedCellsArray(ArrayList<ArrayList<Cell>> cells){
        ArrayList<Cell> rv = new ArrayList<>();
        for(int i=0; i< gridSize; i++){
            for(int j=0; j<gridSize; j++){
                rv.add(cells.get(i).get(j));
            }
        }

        Collections.shuffle(rv);
        return rv;
    }

    private void removeNumberInCell(ArrayList<ArrayList<Cell>> cells, Cell cell) {
        int val = cell.getValue();
        cell.setEmpty();

        solver.solveGridCell(cells, cell);

        if(cell.getValue() == val){
            cell.setEmpty();
        }else{
            cell.setValue(val);
        }
    }

    /**rule 1 */
    private boolean isUniqueInRow(ArrayList<ArrayList<Cell>> cells, Cell cell){
        for(int j = 0; j< gridSize; j++){
            if(cells.get((int)cell.getCoordinates().x).get(j).isEmpty()){
                return false;
            }
        }

        return true;
    }

    /**rule 2 */
    private boolean isUniqueInCol(ArrayList<ArrayList<Cell>> cells, Cell cell){
        for(int i = 0; i< gridSize; i++){
            if(cells.get(i).get((int)cell.getCoordinates().y).isEmpty()){
                return false;
            }
        }

        return true;
    }

    /**rule 3 */
    private boolean isUniqueInBlock(ArrayList<ArrayList<Cell>> cells, Cell cell){
        //get block coordinate this cell resides in
        Vector2 blockCooord = new Vector2((int)(cell.getCoordinates().x / blockSize), (int)(cell.getCoordinates().y / blockSize));

        //get all cells in this block
        ArrayList<Cell> blockCells = new ArrayList<>();
        for(int i = 0; i<blockSize; i++){
            for(int j=0; j< blockSize; j++){

            }
        }

        for(int i = 0; i< gridSize; i++){
            if(cells.get(i).get((int)cell.getCoordinates().y).isEmpty()){
                return false;
            }
        }

        return true;
    }
}
