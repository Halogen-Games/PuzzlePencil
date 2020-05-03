package com.games.halogen.puzzlePencil.sudoku.scene.view.grid;

import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.sudoku.scene.view.PuzzlePencilObject;
import com.games.halogen.puzzlePencil.sudoku.scene.view.cell.Cell;
import com.games.halogen.puzzlePencil.sudoku.utils.SudokuGenerator;

import java.util.ArrayList;

public class SudokuGrid extends PuzzlePencilObject {
    private PuzzlePencilInjector injector;

    private int numBlocks;
    private int numRows;
    private int level;

    private ArrayList<ArrayList<Cell>> cells;

    public SudokuGrid(int numBlocks, int level) {
        this.numBlocks = numBlocks;
        this.numRows = numBlocks*numBlocks;
        this.level = level;
    }

    @Override
    public void init() {
        injector = getCallbacks().getDependencyInjector();

        setupGrid();

        SudokuGenerator.generate(this);

        this.setSize(injector.getLayoutManager().gridSize, injector.getLayoutManager().gridSize);
        this.setPosition(injector.getLayoutManager().gridPos.x, injector.getLayoutManager().gridPos.y);
    }

    private void setupGrid() {
        createCells();
        addChildObject(new com.games.halogen.puzzlePencil.sudoku.scene.view.grid.SudokuLines());
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
    View related functions
     */
    @Override
    public void modelUpdated() {
        //todo: fill
    }

    @Override
    public void layout() {
        this.setPosition(injector.getLayoutManager().gridPos.x, injector.getLayoutManager().gridPos.y);
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
