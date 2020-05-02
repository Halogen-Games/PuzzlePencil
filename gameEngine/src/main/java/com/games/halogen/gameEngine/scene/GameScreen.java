package com.games.halogen.gameEngine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.gameEngine.scene.world.gameObjects.GameObject;

import java.util.Locale;

public abstract class GameScreen implements Screen {
    private Color clearColor;
    private final GameDependencyInjector injector;
    private GameWorld gameWorld;

    public GameScreen(GameDependencyInjector injector){
        this.injector = injector;
        this.setClearColor(1,1,1,1);
    }

    protected void setGameWorld(GameWorld gameWorld){
        this.gameWorld = gameWorld;
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

        injector.resize();

        injector.getRenderer().batch.setProjectionMatrix(injector.getCamera().combined);

        for(GameObject obj: gameWorld.getGameObjects()){
            obj.invalidate();
        }
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
