package com.games.halogen.puzzlePencil.sudoku.world;

import com.games.halogen.gameEngine.scene.world.GameWorldCallbacks;
import com.games.halogen.puzzlePencil.sudoku.view.grid.Cell;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;

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

    public void createNewGrid() {
        getWorld().createNewGrid();
    }

    public SudokuLayoutManager getLayoutManager() {
        return getWorld().getLayoutManager();
    }

    public SudokuWorldData getData(){
        return getWorld().getData();
    }

    public void resetActiveCell() {
        getWorld().resetActiveCell();
    }

    public void setActiveCell(Cell c) {
        getWorld().setActiveCell(c);
    }

    public void toggleNumInActiveCell(int num) {
        getWorld().toggleInActiveCell(num);
    }


    public void toggleMiniumInActiveCell(int num) {
        getWorld().toggleMiniumInActiveCell(num);
    }

    public void nextButtonRevealed(boolean isRevealed) {
        getWorld().nextButtonRevealed(isRevealed);
    }
}
