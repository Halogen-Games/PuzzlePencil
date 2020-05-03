package com.games.halogen.gameEngine.scene.model;


import com.games.halogen.gameEngine.scene.view.GameObject;

public class GameObjectModel {
    private GameObject obj;
    public GameObjectModel(GameObject obj){
        this.obj = obj;
    }

    public final void updateView(){
        obj.modelUpdated();
    }
}
