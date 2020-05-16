package com.games.halogen.puzzlePencil.sudoku.scene.view.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.games.halogen.puzzlePencil.sudoku.scene.view.ui.general.SudokuTextButton;
import com.games.halogen.puzzlePencil.sudoku.scene.world.SudokuLayoutManager;

public class NextButton extends SudokuTextButton {

    public NextButton(String text) {
        super(text);
    }

    protected void addUIListener(){
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getCallbacks().getData().levelPlus();
                getCallbacks().createNewGrid();
                NextButton.this.setVisible(false);
            }
        });
    }

    @Override
    public void layout() {
        super.layout();

        SudokuLayoutManager lm = getCallbacks().getLayoutManager();

        float textHeight = lm.nextButtTextHeightRatio * getHeight();
        getTextButton().setTextHeight(textHeight);
    }
}
