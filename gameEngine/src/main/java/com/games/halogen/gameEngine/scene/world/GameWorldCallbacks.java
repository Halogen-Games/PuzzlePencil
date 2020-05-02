package com.games.halogen.gameEngine.scene.world;

import com.games.halogen.gameEngine.infra.GameDependencyInjector;

public abstract class GameWorldCallbacks {
    private final GameWorld world;

    public GameWorldCallbacks(GameWorld world){
        this.world = world;
    }

    @SuppressWarnings("SameParameterValue")
    protected <T extends GameDependencyInjector> T getGameDependencyInjector(Class<T> type){
        return world.getGameDependencyInjector(type);
    }

    @SuppressWarnings("SameParameterValue")
    protected <T extends GameWorld> T getGameWorld(Class<T> type){
        return type.cast(world);
    }
}
