package com.games.halogen.puzzlePencil.infra;

import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.puzzlePencil.games.sudoku.infra.SudokuData;

public class PuzzlePencilInjector extends GameDependencyInjector {
    public SudokuData sudokuData;

    public PuzzlePencilInjector(float width, float height) {
        super(width, height);
    }

    @Override
    public void initDependencies() {
        this.sudokuData = new SudokuData();
    }

    @Override
    public void setAssetManager() {
        this.gameAssetManager = new PuzzlePencilAssetManager();
    }

    public PuzzlePencilAssetManager getAssetManager(){
        return getGameAssetManager(PuzzlePencilAssetManager.class);
    }
}
