package com.games.halogen.gameEngine.renderer;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.renderer.gameArt.ShapeCreator;

public class ObjectRenderer {
    private final GameDependencyInjector injector;

    public PolygonSpriteBatch batch;
    public ShapeCreator shaper;

    public ObjectRenderer(GameDependencyInjector injector){
        this.injector = injector;
    }

    public void init(){
        batch = new PolygonSpriteBatch();
        shaper = new ShapeCreator(batch, injector.getViewport());
        shaper.init();
    }

    public void dispose(){
        batch.dispose();
        shaper.dispose();
    }
}
