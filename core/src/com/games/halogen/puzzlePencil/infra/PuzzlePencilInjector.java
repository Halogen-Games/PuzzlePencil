package com.games.halogen.puzzlePencil.infra;

import com.games.halogen.gameEngine.infra.GameDependencyInjector;

public class PuzzlePencilInjector extends GameDependencyInjector {

    public PuzzlePencilInjector(float width, float height) {
        super(width, height);
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
}
