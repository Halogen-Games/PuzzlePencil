package com.games.halogen.puzzlePencil.infra;

import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.puzzlePencil.sudoku.infra.SudokuData;

public class PuzzlePencilInjector extends GameDependencyInjector {
    public SudokuData sudokuData;

    public PuzzlePencilInjector(float width, float height) {
        super(width, height);

        sudokuData = new SudokuData();
    }

    @Override
    public void initDependencies() {
    }

    @Override
    public void setAssetManager() {
        this.gameAssetManager = new PuzzlePencilAssetManager();
    }

    public PuzzlePencilAssetManager getAssetManager(){
        return getGameAssetManager(PuzzlePencilAssetManager.class);
    }

    @Override
    public void setLayoutManager() {
        this.gameLayoutManager = new PuzzlePencilLayoutManager();
    }

    public PuzzlePencilLayoutManager getLayoutManager(){
        return getGameLayoutManager(PuzzlePencilLayoutManager.class);
    }
}
