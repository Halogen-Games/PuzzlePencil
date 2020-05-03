package com.games.halogen.gameEngine.scene.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.infra.SwitchScreenCallback;
import com.games.halogen.gameEngine.scene.view.GameObject;

public abstract class GameWorld extends Stage {
    private final GameDependencyInjector injector;
    private SwitchScreenCallback switchScreenCallbacks;

    protected GameWorldCallbacks callbacks;
    protected GameLayoutManager layoutManager;


    public GameWorld(GameDependencyInjector injector){
        super(injector.getViewport(), injector.getRenderer().batch);
        this.injector = injector;
        setLayoutManager();
        setWorldCallbacks();
        initWorldSpecificDependencies();
    }

    protected abstract void initWorldSpecificDependencies();
    protected abstract void setWorldCallbacks();
    protected abstract void setLayoutManager();

    protected final void addGameObject(GameObject obj){
        if(obj != null) {
            Gdx.app.log(this.getClass().getSimpleName(), String.format("Adding Object %s", obj.getClass().getSimpleName()));
            addActor(obj);
            obj.init();
        }
    }

    protected <T extends GameDependencyInjector> T getGameDependencyInjector(Class<T> type){
        if(injector == null){
            return null;
        }
        return type.cast(injector);
    }

    protected <T extends GameWorldCallbacks> T getGameWorldCallbacks(Class<T> type){
        return type.cast(callbacks);
    }

    protected <T extends GameLayoutManager> T getGameLayoutManager(Class<T> type){
        return type.cast(layoutManager);
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

    public void resize(){
        layoutManager.updateLayout(injector);
    }

    /*Screen Switching*/
    public void setSwitchScreenCallback(SwitchScreenCallback callback){
        this.switchScreenCallbacks = callback;
    }
    void switchScreen(Screen screen){
        switchScreenCallbacks.switchScreen(screen);
    }
}
