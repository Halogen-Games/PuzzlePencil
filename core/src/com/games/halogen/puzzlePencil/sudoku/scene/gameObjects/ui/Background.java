package com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.games.halogen.gameEngine.scene.world.gameObjects.GameObject;
import com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.PuzzlePencilObject;

public class Background extends PuzzlePencilObject {
    @Override
    public void init() {
        Image img = new Image(getCallbacks().getDependencyInjector().getAssetManager().getSquareRegion());
        img.setColor(getCallbacks().getDependencyInjector().getLayoutManager().bgColor);
        img.setSize(getCallbacks().getDependencyInjector().getVirtualWidth(),getCallbacks().getDependencyInjector().getVirtualHeight());
        this.addActor(img);
        //This is a change
    }
}
