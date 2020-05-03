package com.games.halogen.puzzlePencil.sudoku.scene.world;

import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.SudokuGrid;
import com.games.halogen.puzzlePencil.sudoku.scene.view.ui.Background;
import com.games.halogen.puzzlePencil.sudoku.scene.view.ui.NextButton;

public class SudokuWorld extends GameWorld {
    private SudokuGrid sudokuGrid;

    public SudokuWorld(PuzzlePencilInjector injector){
        super(injector);
        sudokuGrid = new SudokuGrid(injector.sudokuData.numBlocks, injector.sudokuData.level);
        this.addGameObject(new Background());
        this.addGameObject(sudokuGrid);

        this.addGameObject(new NextButton());
    }

    @Override
    protected void setGameWorldCallbacks() {
        this.callbacks = new SudokuWorldCallbacks(this);
    }

    public SudokuWorldCallbacks getWorldCallbacks(){
        return getGameWorldCallbacks(SudokuWorldCallbacks.class);
    }

    public PuzzlePencilInjector getDependencyInjector(){
        return getGameDependencyInjector(PuzzlePencilInjector.class);
    }

    public void createNewGrid() {
        sudokuGrid.recreateGrid();
    }
}
