package com.games.halogen.gameEngine.scene.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.scene.view.GameObject;

public abstract class GameWorld extends Stage {
    protected GameWorldCallbacks callbacks;
    private final GameDependencyInjector injector;

    public GameWorld(GameDependencyInjector injector){
        super(injector.getViewport(), injector.getRenderer().batch);
        this.injector = injector;
        setGameWorldCallbacks();
    }

    protected <T extends GameDependencyInjector> T getGameDependencyInjector(Class<T> type){
        if(injector == null){
            return null;
        }
        return type.cast(injector);
    }

    public <T extends GameWorldCallbacks> T getGameWorldCallbacks(Class<T> type){
        return type.cast(callbacks);
    }

    protected abstract void setGameWorldCallbacks();

    @SuppressWarnings("WeakerAccess")
    public final void addGameObject(GameObject obj){
        if(obj != null) {
            Gdx.app.log(this.getClass().getSimpleName(), String.format("Adding Object %s", obj.getClass().getSimpleName()));
            addActor(obj);
            obj.init();
        }
    }

    public Array<GameObject> getGameObjects(){
        Array<GameObject> rv = new Array<>();
        for(Actor act: getActors()){
            GameObject obj;
            try {
                obj = (GameObject) act;
            }catch (Exception e){
                throw new RuntimeException(String.format("Actor %s added to GameWorld is not castable to GameObject", act.getClass().getSimpleName()));
            }
            rv.add(obj);
        }

        return rv;
    }
}
