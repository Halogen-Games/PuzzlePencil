package com.games.halogen.gameEngine.infra;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.games.halogen.gameEngine.renderer.ObjectRenderer;

public abstract class GameDependencyInjector {
    //World Data
    private final float virtualWidth;
    private final float virtualHeight;

    //Camera and Viewport
    private ExtendViewport viewport;
    private OrthographicCamera cam;

    //Renderer
    private ObjectRenderer renderer;
    protected GameAssetManager gameAssetManager;

    //Layout
    protected GameLayoutManager gameLayoutManager;

    public GameDependencyInjector(float virtualWidth, float virtualHeight){
        this.virtualWidth = virtualWidth;
        this.virtualHeight = virtualHeight;
    }

    public final void init(){
        //Camera and Viewport
        cam = new OrthographicCamera(virtualWidth, virtualHeight);
        cam.setToOrtho(false, virtualWidth, virtualHeight);
        viewport = new ExtendViewport(virtualWidth, virtualHeight, cam);

        cam.position.set(viewport.getWorldWidth()/2f, viewport.getWorldHeight()/2f, 0);

        //Painter
        renderer = new ObjectRenderer(this);
        renderer.init();

        AssetManager manager = new AssetManager();
        setAssetManager();
        gameAssetManager.beginLoading(manager);
        initDependencies();

        //Layout
        setLayoutManager();
    }

    public abstract void setAssetManager();

    protected <T extends GameAssetManager> T getGameAssetManager(Class<T> type){
        return type.cast(gameAssetManager);
    }

    public abstract void setLayoutManager();

    protected <T extends GameLayoutManager> T getGameLayoutManager(Class<T> type){
        return type.cast(gameLayoutManager);
    }

    public abstract void initDependencies();

    public void dispose(){
        renderer.dispose();
        gameAssetManager.dispose();
    }

    public Camera getCamera() {
        return cam;
    }

    public Viewport getViewport(){
        return viewport;
    }

    public ObjectRenderer getRenderer(){
        return renderer;
    }

    public float getVirtualWidth(){
        return virtualWidth;
    }

    public float getVirtualHeight(){
        return virtualHeight;
    }

    public void resize(){
        gameLayoutManager.updateLayout(this);
    }
}
