package com.games.halogen.puzzlePencil.games.sudoku.scene.view.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.ui.general.SudokuTextButton;
import com.games.halogen.puzzlePencil.games.sudoku.scene.world.SudokuLayoutManager;

public class NextButton extends SudokuTextButton {

    public NextButton(String text) {
        super(text);
    }

    protected void addUIListener(){
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getCallbacks().createNewGrid();
            }
        });
    }

    @Override
    public void layout() {
        super.layout();

        SudokuLayoutManager lm = getCallbacks().getLayoutManager();
        this.setSize(lm.nextButtSize.x, lm.nextButtSize.y);

        float textHeight = lm.nextButtTextHeightRatio * getHeight();
        getTextButton().setTextHeight(textHeight);

        Viewport vp = getCallbacks().getDependencyInjector().getViewport();
        this.setPosition((vp.getWorldWidth() - getWidth())/2,100);
    }
}
