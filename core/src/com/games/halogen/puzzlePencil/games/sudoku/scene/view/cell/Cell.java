package com.games.halogen.puzzlePencil.games.sudoku.scene.view.cell;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.games.halogen.gameEngine.utils.Pair;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.SudokuObject;
import com.games.halogen.puzzlePencil.games.sudoku.scene.world.SudokuLayoutManager;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;

public class Cell extends SudokuObject {
    private Label valueLabel;
    private Pair.IntPair coordinates;
    private Miniums miniums;

    private boolean isSelected;

    private Image bgImg;

    public Cell(int r, int c){
        this.coordinates = new Pair.IntPair(r,c);
    }

    @Override
    public void init() {
        PuzzlePencilInjector di = getCallbacks().getDependencyInjector();
        SudokuLayoutManager lm = getCallbacks().getLayoutManager();

        int numRows = getCallbacks().getData().numBlocks * getCallbacks().getData().numBlocks;
        float cellSize = (lm.gridSize / numRows) * (1 - lm.cellMarginToSizeRatio);
        lm.cellSize = cellSize;
        float cellMargin = (lm.gridSize / numRows) * lm.cellMarginToSizeRatio / 2;

        this.setSize(cellSize + 2*cellMargin, cellSize + 2*cellMargin);
        this.setPosition(this.coordinates.getFirst()*getWidth(), this.coordinates.getSecond()*getHeight());

        bgImg = new Image(di.getAssetManager().getSquareRegion());
        bgImg.setSize(cellSize, cellSize);
        bgImg.setPosition(cellMargin, cellMargin);
        bgImg.setColor(lm.deselectedCellColor);
        this.addActor(bgImg);

        valueLabel = new Label("", di.getAssetManager().fontLabelStyle);
        valueLabel.setColor(lm.fontColor);
        valueLabel.setAlignment(Align.center);
        valueLabel.setSize(bgImg.getWidth(), bgImg.getHeight());
        float fontScale = cellSize * lm.cellTextRatio / valueLabel.getPrefHeight();
        valueLabel.setFontScale(fontScale);
        this.addActor(valueLabel);

        isSelected  = false;
        addUIListeners();
    }

    private void addUIListeners() {
        final Cell thisCell = this;
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getCallbacks().resetActiveCell();
                setActive(true);
                getCallbacks().setActiveCell(thisCell);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void setActive(boolean isSelected) {
        this.isSelected = isSelected;
        if(this.isSelected){
            bgImg.setColor(getCallbacks().getLayoutManager().selectedCellColor);
        }else{
            bgImg.setColor(getCallbacks().getLayoutManager().deselectedCellColor);
        }
    }

    @Override
    public void modelUpdated() {
        //todo: fill
    }

    public void setValue(int val) {
        if(val == -1){
            setEmpty();
        }else {
            valueLabel.setText(val);
        }
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
