package com.games.halogen.gameEngine.scene.world;

import com.badlogic.gdx.Screen;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;

public abstract class GameWorldCallbacks {
    private final GameWorld world;

    public GameWorldCallbacks(GameWorld world){
        this.world = world;
    }

    protected <T extends GameDependencyInjector> T getGameDependencyInjector(Class<T> type){
        return world.getGameDependencyInjector(type);
    }

    protected <T extends GameWorld> T getGameWorld(Class<T> type){
        return type.cast(world);
    }

    public final void switchScreen(Screen scr){
        world.switchScreen(scr);
    }
}
