package com.games.halogen.puzzlePencil.sudoku.scene.view.ui.general;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.games.halogen.puzzlePencil.scene.view.PuzzlePencilObject;

public class TextLabel extends PuzzlePencilObject {
    private Label label;

    private float scale;
    private int alignment;

    public TextLabel(String text, LabelStyle style){
        this.label = new Label(text, style);
        this.addActor(this.label);

        this.scale = 1;
    }

    @Override
    public void init() {

    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        this.label.setSize(width, height);

        this.setOrigin(this.alignment);
    }

    public void setTextHeight(float h){
        this.scale = h / this.label.getPrefHeight();

        this.setScale(scale);
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
        this.setOrigin(alignment);
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
