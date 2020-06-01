package com.games.halogen.puzzlePencil.sudoku.viewOld.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.games.halogen.puzzlePencil.sudoku.viewOld.SudokuObject;

public class Background extends SudokuObject {
    private Image img;
    @Override
    public void init() {
        img = new Image(getCallbacks().getDependencyInjector().getAssetManager().getSquareRegion());
        img.setColor(getCallbacks().getLayoutManager().bgColor);
        this.addActor(img);
    }

    @Override
    public void layout() {
        super.layout();
        img.setSize(getWidth(), getHeight());
    }
}
