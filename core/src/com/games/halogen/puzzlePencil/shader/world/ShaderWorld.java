package com.games.halogen.puzzlePencil.shader.world;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.renderer.shaders.primitives.ShadedBuffer;
import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.shader.shaderPrograms.Mandelbrot;
import com.games.halogen.puzzlePencil.shader.shaderPrograms.TestShader1;

public class ShaderWorld extends GameWorld {
    private ShadedBuffer shadeBuff;

    public ShaderWorld(GameDependencyInjector injector) {
        super(injector);
    }

    @Override
    protected void initWorldSpecificDependencies() {
        //nothing to init
        int width = (int)getDependencyInjector().getVirtualWidth();
        int height = (int)getDependencyInjector().getVirtualHeight();

        shadeBuff = new ShadedBuffer(width, height, new TestShader1());
        this.addGameObject(shadeBuff);
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
        shadeBuff.setSize(getDependencyInjector().getViewport().getWorldWidth(), getDependencyInjector().getViewport().getWorldHeight());
    }
}
