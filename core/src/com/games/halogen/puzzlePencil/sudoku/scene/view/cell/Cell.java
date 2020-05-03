package com.games.halogen.puzzlePencil.sudoku.scene.view.cell;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.games.halogen.gameEngine.utils.Pair;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilLayoutManager;
import com.games.halogen.puzzlePencil.sudoku.scene.view.PuzzlePencilObject;

public class Cell extends PuzzlePencilObject {
    private Label valueLabel;
    private Pair.IntPair coordinates;
    private Miniums miniums;

    public Cell(int r, int c){
        this.coordinates = new Pair.IntPair(r,c);
    }

    @Override
    public void init() {
        PuzzlePencilInjector di = getCallbacks().getDependencyInjector();
        PuzzlePencilLayoutManager lm = di.getLayoutManager();

        float cellSize = (lm.gridSize / getCallbacks().getNumRows()) * (1 - lm.cellMarginToSizeRatio);
        lm.cellSize = cellSize;
        float cellMargin = (lm.gridSize / getCallbacks().getNumRows()) * lm.cellMarginToSizeRatio / 2;

        this.setSize(cellSize + 2*cellMargin, cellSize + 2*cellMargin);
        this.setPosition(this.coordinates.getFirst()*getWidth(), this.coordinates.getSecond()*getHeight());

        Image img = new Image(di.getAssetManager().getSquareRegion());
        img.setSize(cellSize, cellSize);
        img.setPosition(cellMargin, cellMargin);
        img.setColor(lm.cellColor);
        this.addActor(img);

        valueLabel = new Label("", di.getAssetManager().fontLabelStyle);
        valueLabel.setColor(lm.fontColor);
        valueLabel.setAlignment(Align.center);
        valueLabel.setSize(img.getWidth(), img.getHeight());
        float fontScale = cellSize * lm.cellTextRatio / valueLabel.getPrefHeight();
        valueLabel.setFontScale(fontScale);
        this.addActor(valueLabel);
    }

    @Override
    public void modelUpdated() {
        //todo: fill
    }

    public void setValue(int val) {
        valueLabel.setText(val);
    }

    public int getValue() {
        if(valueLabel.getText().toString().equals("")){
            return -1;
        }
        return Integer.parseInt(valueLabel.getText().toString());
    }

    public void setEmpty() {
        valueLabel.setText("");
    }

    public boolean isEmpty(){
        return valueLabel.getText().toString().equals("");
    }

    public void setMiniums(Miniums miniums) {
        this.miniums = miniums;
    }

    public Miniums getMiniums(){
        return this.miniums;
    }

    public Pair.IntPair getCoordinates(){
        return coordinates;
    }
}
