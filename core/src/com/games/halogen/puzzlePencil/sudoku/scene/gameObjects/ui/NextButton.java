package com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.games.halogen.gameEngine.ui.GameTextButton;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilLayoutManager;
import com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.PuzzlePencilObject;

public class NextButton extends PuzzlePencilObject {
    private GameTextButton textButton;
    @Override
    public void init() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = getCallbacks().getDependencyInjector().getAssetManager().fontLabelStyle.font;
        style.fontColor = getCallbacks().getDependencyInjector().getLayoutManager().fontColor;

        Sprite upState = new Sprite(getCallbacks().getDependencyInjector().getAssetManager().getSquareRegion());
        upState.setColor(getCallbacks().getDependencyInjector().getLayoutManager().buttonBGColor);

        style.up = new SpriteDrawable(upState);

        textButton = new GameTextButton("Next Puzzle", style);

        this.addChildObject(textButton);

        addUIListener();
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

        PuzzlePencilLayoutManager lm = getCallbacks().getDependencyInjector().getLayoutManager();
        this.setSize(lm.nextButtSize.x, lm.nextButtSize.y);
        textButton.setSize(getWidth(), getHeight());
        float textHeight = lm.nextButtTextHeightRatio * getHeight();
        textButton.setTextHeight(textHeight);

        Viewport vp = getCallbacks().getDependencyInjector().getViewport();
        this.setPosition((vp.getWorldWidth() - getWidth())/2,100);
    }
}
