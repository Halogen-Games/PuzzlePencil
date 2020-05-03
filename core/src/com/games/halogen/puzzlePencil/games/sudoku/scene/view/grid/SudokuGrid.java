package com.games.halogen.puzzlePencil.games.sudoku.scene.view.grid;

import com.games.halogen.puzzlePencil.games.sudoku.scene.view.SudokuObject;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.cell.Cell;
import com.games.halogen.puzzlePencil.games.sudoku.utils.SudokuGenerator;

import java.util.ArrayList;

public class SudokuGrid extends SudokuObject {
    private int numBlocks;
    private int numRows;
    private int level;

    private ArrayList<ArrayList<Integer>> gridStates;
    private ArrayList<ArrayList<Cell>> cells;

    public SudokuGrid(int numBlocks, int level) {
        this.numBlocks = numBlocks;
        this.numRows = numBlocks*numBlocks;
        this.level = level;
    }

    @Override
    public void init() {
        setupGrid();

        SudokuGenerator.generate(this);
    }

    private void setupGrid() {
        createCells();
        addChildObject(new com.games.halogen.puzzlePencil.games.sudoku.scene.view.grid.SudokuLines());
    }

    private void createCells() {
        cells = new ArrayList<>();
        for(int i = 0; i< numRows; i++){
            ArrayList<Cell> row = new ArrayList<>();
            for(int j = 0; j< numRows; j++){
                Cell c = new Cell(i, j);
                row.add(c);
                addChildObject(c);
            }
            cells.add(row);
        }
    }

    public void recreateGrid() {
        SudokuGenerator.generate(this);
    }

    /*
    Functions specific to the grid's view
     */
    @Override
    public void modelUpdated() {
        //todo: fill
    }

    @Override
    public void layout() {
        this.setSize(getCallbacks().getLayoutManager().gridSize, getCallbacks().getLayoutManager().gridSize);
        this.setPosition(getCallbacks().getLayoutManager().gridPos.x, getCallbacks().getLayoutManager().gridPos.y);
    }

    /*
    Setters and getters
     */
    public void setValue(int i, int j, int value) {
        cells.get(i).get(j).setValue(value);
    }

    public int getValue(int i, int j) {
        return cells.get(i).get(j).getValue();
    }

    public Cell getCell(int i, int j) {
        return cells.get(i).get(j);
    }

    public int getLevel() {
        return this.level;
    }

    public int getBlockSize() {
        return numBlocks;
    }

    public int getNumRows() {
        return numRows;
    }
}
