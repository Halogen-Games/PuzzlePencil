package com.games.halogen.puzzlePencil.sudoku.scene.view.ui.general;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.games.halogen.puzzlePencil.scene.view.PuzzlePencilObject;

public class TextLabel extends PuzzlePencilObject {
    private Label label;

    private float scale;
    private Vector2 alignment;

    public TextLabel(String text, LabelStyle style){
        this.label = new Label(text, style);
        this.label.setAlignment(Align.center);
        this.addActor(this.label);

        this.alignment = new Vector2(0.5f, 0.5f);
        this.scale = 1;
    }

    @Override
    public void init() {

    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        this.label.setSize(width, height);

        this.setOrigin(this.alignment.x * getWidth(), this.alignment.y * getHeight());
    }

    public void setTextHeight(float h){
        this.scale = h / this.label.getPrefHeight();

        this.setScale(scale);
    }

    public void setAlignment(float x, float y) {
        this.alignment.set(x,y);
        this.setOrigin(this.alignment.x * getWidth(), this.alignment.y * getHeight());
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        label.setColor(color);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        super.setColor(r, g, b, a);
        label.setColor(r,g,b,a);
    }

    public void setText(int val) {
        this.label.setText(val);
    }

    public void setText(String s) {
        this.label.setText(s);
    }

    public String getText() {
        return label.getText().toString();
    }
}
