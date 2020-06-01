package com.games.halogen.puzzlePencil.sudoku.world;

import com.games.halogen.gameEngine.scene.world.GameWorldCallbacks;
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

    public void deselectCell() {
        getWorld().deselectCell();
    }

    public void selectCell(int r, int c) {
        getWorld().selectCell(r, c);
    }

    public void toggleNumInSelectedCell(int num) {
        System.out.println("Toggling");
        getWorld().toggleInSelectedCell(num);
    }


    public void toggleVisibleInSelectedCell(int num) {
        getWorld().toggleVisibleInSelectedCell(num);
    }

    public void nextButtonRevealed(boolean isRevealed) {
        getWorld().nextButtonRevealed(isRevealed);
    }
}
