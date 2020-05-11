package com.games.halogen.gameEngine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.infra.SwitchScreenCallback;
import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.gameEngine.scene.view.GameObject;

import java.util.Locale;

public abstract class GameScreen implements Screen {
    private final GameDependencyInjector injector;

    private Color clearColor;
    private GameWorld gameWorld;

    private SwitchScreenCallback switchScreenCallback;

    public GameScreen(GameDependencyInjector injector, SwitchScreenCallback switchScreenCallback){
        this.injector = injector;
        this.switchScreenCallback = switchScreenCallback;
        this.setClearColor(1,1,1,1);
    }

    protected void setGameWorld(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        this.gameWorld.setSwitchScreenCallback(switchScreenCallback);
        Gdx.input.setInputProcessor(gameWorld);
    }

    @SuppressWarnings("SameParameterValue")
    protected final void setClearColor(float r, float g, float b, float a){
        this.clearColor = new Color(r,g,b,a);
    }

    @Override
    public final void render(float delta) {
        Gdx.gl20.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        gameWorld.act(delta);
        gameWorld.draw();
    }

    @Override
    public final void show() {

    }

    @Override
    public final void hide() {

    }

    @Override
    public final void resize(int width, int height) {
        Gdx.app.log(this.getClass().getSimpleName(), String.format(Locale.US,"Resizing to (%d, %d)", width, height));
        injector.getViewport().update(width, height, true);
        injector.getCamera().update();

        injector.getRenderer().batch.setProjectionMatrix(injector.getCamera().combined);

        gameWorld.resize();
    }

    @Override
    public final void pause() {

    }

    @Override
    public final void resume() {

    }

    @Override
    public final void dispose() {
        gameWorld.dispose();
    }
}
