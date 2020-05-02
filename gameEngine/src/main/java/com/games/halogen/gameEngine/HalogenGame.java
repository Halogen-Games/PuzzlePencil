package com.games.halogen.gameEngine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;

public abstract class HalogenGame extends Game {
    protected GameDependencyInjector di;

    @Override
    public final void create() {
        setDependencyInjector();
        di.init();
        this.init();
    }

    protected abstract void init();

    protected <T extends GameDependencyInjector> T getGameDependencyManager(@SuppressWarnings("SameParameterValue") Class<T> type){
        return type.cast(di);
    }

    protected abstract void setDependencyInjector();

    @Override
    public final void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public final void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        di.dispose();
    }
}
