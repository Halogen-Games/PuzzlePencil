package com.games.halogen.puzzlePencil.games.sudoku.scene.world;

import com.games.halogen.gameEngine.scene.world.GameWorldCallbacks;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.scene.world.PuzzlePencilLayoutManager;

public class SudokuWorldCallbacks extends GameWorldCallbacks {
    SudokuWorldCallbacks(SudokuWorld world){
        super(world);
    }

    public PuzzlePencilInjector getDependencyInjector() {
        return getGameDependencyInjector(PuzzlePencilInjector.class);
    }

    private SudokuWorld getWorld(){
        return getGameWorld(SudokuWorld.class);
    }

    public int getNumRows() {
        int numBlocks = getDependencyInjector().sudokuData.numBlocks;
        return numBlocks * numBlocks;
    }

    public void createNewGrid() {
        getWorld().createNewGrid();
    }

    public SudokuLayoutManager getLayoutManager() {
        return getWorld().getLayoutManager();
    }
}
