package com.games.halogen.puzzlePencil.games.sudoku.scene.world;

import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.grid.SudokuGrid;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.ui.Background;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.ui.NextButton;

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
    protected void setWorldCallbacks() {
        this.callbacks = new SudokuWorldCallbacks(this);
    }

    @Override
    protected void setLayoutManager() {
        this.layoutManager = new SudokuLayoutManager();
    }

    @Override
    protected void initWorldSpecificDependencies() {

    }

    void createNewGrid() {
        sudokuGrid.recreateGrid();
    }

    SudokuWorldCallbacks getWorldCallbacks(){
        return getGameWorldCallbacks(SudokuWorldCallbacks.class);
    }

    SudokuLayoutManager getLayoutManager(){
        return getGameLayoutManager(SudokuLayoutManager.class);
    }

    PuzzlePencilInjector getDependencyInjector(){
        return getGameDependencyInjector(PuzzlePencilInjector.class);
    }

    public SudokuWorldCallbacks getCallbacks() {
        return getGameWorldCallbacks(SudokuWorldCallbacks.class);
    }
}
