package com.games.halogen.gameEngine.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Scaling;
import com.games.halogen.gameEngine.scene.view.GameObject;

public class GameTextButton extends GameObject {
    private final TextButton button;
    private float buttonTextHeight;

    public GameTextButton(String text, TextButton.TextButtonStyle buttonStyle) {
        button = new TextButton(text, buttonStyle);
        button.setTransform(true);
        this.addActor(button);
        buttonTextHeight = -1;
    }

    @Override
    public void layout() {
        super.layout();

        float maxTextHeight = (buttonTextHeight == -1) ? getHeight() : Math.min(buttonTextHeight,getHeight());
        Vector2 sz = Scaling.fit.apply(button.getPrefWidth(), button.getPrefHeight(), getWidth(), maxTextHeight);
        float scale = (button.getPrefHeight() == 0) ? button.getScaleX() : sz.x / button.getPrefWidth();

        button.setSize(getWidth()/scale, getHeight()/scale);
        button.setScale(scale);
    }

    @Override
    public void init() {
    }

    @Override
    public void modelUpdated() {
        //todo: fill
    }

    public TextButton.TextButtonStyle getStyle(){
        return button.getStyle();
    }

    public void setTextHeight(float h){
        buttonTextHeight = h;
        invalidate();
    }
}
