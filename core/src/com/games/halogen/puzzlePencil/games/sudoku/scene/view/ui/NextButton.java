package com.games.halogen.puzzlePencil.games.sudoku.scene.view.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.games.halogen.gameEngine.ui.GameTextButton;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.SudokuObject;
import com.games.halogen.puzzlePencil.games.sudoku.scene.world.SudokuLayoutManager;
import com.games.halogen.puzzlePencil.scene.world.PuzzlePencilLayoutManager;

public class NextButton extends SudokuObject {
    private GameTextButton textButton;
    @Override
    public void init() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = getCallbacks().getDependencyInjector().getAssetManager().fontLabelStyle.font;
        style.fontColor = getCallbacks().getLayoutManager().fontColor;

        Sprite upState = new Sprite(getCallbacks().getDependencyInjector().getAssetManager().getSquareRegion());
        upState.setColor(getCallbacks().getLayoutManager().buttonBGColor);

        style.up = new SpriteDrawable(upState);

        textButton = new GameTextButton("Next Puzzle", style);

        this.addChildObject(textButton);

        addUIListener();
    }

    @Override
    public void modelUpdated() {
        //todo: fill
    }

    private void addUIListener(){
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
        textButton.setSize(getWidth(), getHeight());
        float textHeight = lm.nextButtTextHeightRatio * getHeight();
        textButton.setTextHeight(textHeight);

        Viewport vp = getCallbacks().getDependencyInjector().getViewport();
        this.setPosition((vp.getWorldWidth() - getWidth())/2,100);
    }
}
