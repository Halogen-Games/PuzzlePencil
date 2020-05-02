package com.games.halogen.gameEngine.scene.world.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.gameEngine.scene.world.GameWorldCallbacks;

public abstract class GameObject extends WidgetGroup {
    public abstract void init();

    protected <T extends GameWorldCallbacks> T getGameWorldCallbacks(Class<T> type){
        if(getWorld() == null){
            Gdx.app.log(this.getClass().getSimpleName(), "Failed to get world callbacks, object not added to world");
            return null;
        }

        return getWorld().getGameWorldCallbacks(type);
    }

    private GameWorld getWorld(){
        if(super.getStage() == null){
            return null;
        }
        return (GameWorld)super.getStage();
    }

    protected void addChildObject(GameObject obj){
        if(obj != null) {
            Gdx.app.log(this.getClass().getSimpleName(), String.format("Adding Object %s", obj.getClass().getSimpleName()));
            super.addActor(obj);
            obj.init();
        }
    }
}
