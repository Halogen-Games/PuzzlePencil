package com.games.halogen.puzzlePencil.games.sudoku.scene.view.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.SudokuObject;

public class Background extends SudokuObject {
    @Override
    public void init() {
        Image img = new Image(getCallbacks().getDependencyInjector().getAssetManager().getSquareRegion());
        img.setColor(getCallbacks().getLayoutManager().bgColor);
        img.setSize(getCallbacks().getDependencyInjector().getVirtualWidth(),getCallbacks().getDependencyInjector().getVirtualHeight());
        this.addActor(img);
    }

    @Override
    public void modelUpdated() {
        //todo: fill
    }
}
