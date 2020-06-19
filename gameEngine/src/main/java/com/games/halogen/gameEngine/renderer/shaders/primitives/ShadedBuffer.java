package com.games.halogen.gameEngine.renderer.shaders.primitives;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.games.halogen.gameEngine.infra.GameAssetManager;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.renderer.shaders.shaderPrograms.DefaultShader;
import com.games.halogen.gameEngine.scene.view.GameObject;
import com.games.halogen.gameEngine.scene.world.GameWorld;

public class ShadedBuffer extends GameObject {
    private FrameBuffer fbo;
    private ShaderProgram shader;

    private Viewport vp;

    private TextureRegion squareRegion;
    private Image img;

    private float elapsed;

    public ShadedBuffer(int width, int height){
        this(width, height, new DefaultShader());
    }

    public ShadedBuffer(int width, int height, ShaderProgram shader){
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height,true);
        this.shader = shader;

        this.setSize(width, height);
    }

    @Override
    public void init() {
        this.vp = this.getGameWorld(GameWorld.class).getGameDependencyInjector(GameDependencyInjector.class).getViewport();
        TextureRegion texReg = new TextureRegion(fbo.getColorBufferTexture());
        texReg.flip(false, true);
        this.img = new Image(texReg);
        this.addActor(this.img);

        this.squareRegion = getGameWorld(GameWorld.class).getGameDependencyInjector(GameDependencyInjector.class).getGameAssetManager(GameAssetManager.class).getSquareRegion();
    }

    private void setUniforms(){
        this.shader.setUniformf("u_resolution", fbo.getWidth(), fbo.getHeight());
        this.shader.setUniformf("u_fboHeight", fbo.getHeight());
        this.shader.setUniformf("u_time", elapsed);
    }

    private void drawToBuffer(Batch batch){
        batch.flush();
        batch.setShader(this.shader);
        fbo.begin();
        vp.apply();
        setUniforms();
        System.out.println(fbo.getWidth());
        batch.draw(squareRegion, 0, 0, fbo.getWidth(), fbo.getHeight());
        batch.flush();
        fbo.end();
        vp.apply();
        batch.setShader(null);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsed += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawToBuffer(batch);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void layout() {
        super.layout();
        img.setSize(getWidth(), getHeight());
        //fill this
    }
}
