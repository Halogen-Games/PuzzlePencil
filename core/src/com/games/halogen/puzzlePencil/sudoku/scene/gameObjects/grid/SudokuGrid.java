package com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.grid;

import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.PuzzlePencilObject;
import com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.cell.Cell;
import com.games.halogen.puzzlePencil.sudoku.utils.SudokuGenerator;

import java.util.ArrayList;

public class SudokuGrid extends PuzzlePencilObject {
    private PuzzlePencilInjector injector;

    private int numBlocks;
    private ArrayList<ArrayList<Cell>> cells;

    private SudokuGenerator generator;

    public SudokuGrid(int numBlocks) {
        this.numBlocks = numBlocks;
    }

    @Override
    public void init() {
        injector = getCallbacks().getDependencyInjector();

        setupGrid();

        generator = new SudokuGenerator();
        generator.generate(cells, injector.sudokuData.level);

        this.setSize(injector.getLayoutManager().gridSize, injector.getLayoutManager().gridSize);
        this.setPosition(injector.getLayoutManager().gridPos.x, injector.getLayoutManager().gridPos.y);
    }

    private void setupGrid() {
        createCells();
        addChildObject(new SudokuLines());
    }

    private void createCells() {
        int numRows = numBlocks * numBlocks;
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

    @Override
    public void layout() {
        this.setPosition(injector.getLayoutManager().gridPos.x, injector.getLayoutManager().gridPos.y);
    }

    public void recreate() {
        generator.generate(cells, injector.sudokuData.level);
    }
}
