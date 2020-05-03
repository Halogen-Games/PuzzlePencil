package com.games.halogen.gameEngine.scene.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.gameEngine.scene.world.GameWorldCallbacks;

public abstract class GameObject extends WidgetGroup {
    public abstract void init();

    protected <T extends GameWorld> T getGameWorld(Class<T> type){
        if(this.getStage() == null){
            Gdx.app.log(this.getClass().getSimpleName(), "Failed to get world, object not added to any world");
            return null;
        }
        return type.cast(this.getStage());
    }

    protected void addChildObject(GameObject obj){
        if(obj != null) {
            Gdx.app.log(this.getClass().getSimpleName(), String.format("Adding Object %s", obj.getClass().getSimpleName()));
            super.addActor(obj);
            obj.init();
        }
    }

    public abstract void modelUpdated();
}
