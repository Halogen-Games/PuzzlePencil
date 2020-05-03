package com.games.halogen.puzzlePencil.scene.world;

import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;

public class PuzzlePencilWorld extends GameWorld {

    public PuzzlePencilWorld(GameDependencyInjector injector) {
        super(injector);
    }

    @Override
    protected void initWorldSpecificDependencies() {
        //nothing to init
    }

    @Override
    protected void setWorldCallbacks() {
        this.callbacks = new PuzzlePencilWorldCallbacks(this);
    }

    @Override
    protected void setLayoutManager() {
        this.layoutManager = new PuzzlePencilLayoutManager();
    }

    @Override
    protected void setGameData() {
        this.gameWorldData = new PuzzlePencilWorldData();
    }

    public PuzzlePencilWorldCallbacks getWorldCallbacks(){
        return getGameWorldCallbacks(PuzzlePencilWorldCallbacks.class);
    }

    public PuzzlePencilLayoutManager getLayoutManager(){
        return getGameLayoutManager(PuzzlePencilLayoutManager.class);
    }

    public PuzzlePencilInjector getDependencyInjector(){
        return getGameDependencyInjector(PuzzlePencilInjector.class);
    }
}
