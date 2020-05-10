package com.games.halogen.puzzlePencil.games.sudoku.scene.view.ui.general;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.games.halogen.gameEngine.ui.GameTextButton;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.SudokuObject;

public abstract class SudokuTextButton extends SudokuObject {
    private String text;
    private GameTextButton textButton;

    public SudokuTextButton(String text){
        this.text = text;
    }

    @Override
    public void init() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = getCallbacks().getDependencyInjector().getAssetManager().fontLabelStyle.font;
        style.fontColor = getCallbacks().getLayoutManager().fontColor;

        Sprite upState = new Sprite(getCallbacks().getDependencyInjector().getAssetManager().getSquareRegion());
        upState.setColor(getCallbacks().getLayoutManager().buttonBGColor);

        style.up = new SpriteDrawable(upState);

        textButton = new GameTextButton(text, style);

        this.addChildObject(textButton);

        addUIListener();
    }

    public GameTextButton getTextButton(){
        return textButton;
    }

    protected abstract void addUIListener();

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        textButton.setSize(width, height);
    }
}
