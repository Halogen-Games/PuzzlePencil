package com.games.halogen.gameEngine.scene.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.games.halogen.gameEngine.scene.world.GameWorld;

public abstract class GameObject extends WidgetGroup {
    public abstract void init();

    protected <T extends GameWorld> T getGameWorld(Class<T> type){
        if(this.getStage() == null){
            Gdx.app.log(this.getClass().getSimpleName(), "Failed to get world, object not added to any world");
            return null;
        }
        return type.cast(this.getStage());
    }

    protected void addChildObject(GameObject obj, boolean printLog){
        if(obj != null) {
            if(printLog) {
                Gdx.app.log(this.getClass().getSimpleName(), String.format("Adding Child Object %s", obj.getClass().getSimpleName()));
            }
            super.addActor(obj);
            obj.init();
        }
    }

    protected void addChildObject(GameObject obj){
        addChildObject(obj, false);
    }

    public final boolean isInit(){
        return this.getStage() != null;
    }

    public void modelUpdated(){

    }
}
