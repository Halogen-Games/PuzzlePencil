package com.games.halogen.puzzlePencil.shader.world;

import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.renderer.shaders.primitives.ShadedBuffer;
import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.shader.shaderPrograms.TestShader;
import com.games.halogen.puzzlePencil.shader.view.Scenery;

public class ShaderWorld extends GameWorld {
    private Scenery scenery;
    private ShadedBuffer buffer;
    public ShaderWorld(GameDependencyInjector injector) {
        super(injector);
    }

    @Override
    protected void initWorldSpecificDependencies() {
        //nothing to init
        int width = (int)getDependencyInjector().getVirtualWidth();
        int height = (int)getDependencyInjector().getVirtualHeight();

        scenery = new Scenery(width, height);
        buffer = new ShadedBuffer(width, height, new TestShader());
        this.addGameObject(buffer);
    }

    @Override
    protected void setWorldCallbacks() {
        this.callbacks = new ShaderWorldCallbacks(this);
    }

    @Override
    protected void setLayoutManager() {
        this.layoutManager = new ShaderLayoutManager();
    }

    @Override
    protected void setGameData() {
        this.gameWorldData = new ShaderWorldData();
    }

    public ShaderWorldCallbacks getWorldCallbacks(){
        return getGameWorldCallbacks(ShaderWorldCallbacks.class);
    }

    public ShaderLayoutManager getLayoutManager(){
        return getGameLayoutManager(ShaderLayoutManager.class);
    }

    public PuzzlePencilInjector getDependencyInjector(){
        return getGameDependencyInjector(PuzzlePencilInjector.class);
    }

    @Override
    public void layout() {
        super.layout();
        scenery.setSize(getDependencyInjector().getViewport().getWorldWidth(), getDependencyInjector().getViewport().getWorldHeight());
        buffer.setSize(getDependencyInjector().getViewport().getWorldWidth(), getDependencyInjector().getViewport().getWorldHeight());
    }
}
