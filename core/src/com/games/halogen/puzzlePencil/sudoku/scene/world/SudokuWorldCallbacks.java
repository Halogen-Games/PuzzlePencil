package com.games.halogen.puzzlePencil.sudoku.scene.world;

import com.games.halogen.gameEngine.scene.world.GameWorldCallbacks;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;

public class SudokuWorldCallbacks extends GameWorldCallbacks {
    SudokuWorldCallbacks(SudokuWorld world){
        super(world);
    }

    public PuzzlePencilInjector getDependencyInjector() {
        return getGameDependencyInjector(PuzzlePencilInjector.class);
    }

    public SudokuWorld getWorld(){
        return getGameWorld(SudokuWorld.class);
    }

    public int getNumRows() {
        int numBlocks = getDependencyInjector().sudokuData.numBlocks;
        return numBlocks * numBlocks;
    }

    public void createNewGrid() {
        getWorld().createNewGrid();
    }
}
