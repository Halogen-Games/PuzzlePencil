package com.games.halogen.puzzlePencil.oldSudoku.viewOld.grid;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.games.halogen.gameEngine.utils.Pair.IntPair;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.oldSudoku.viewOld.SudokuObject;
import com.games.halogen.puzzlePencil.sudoku.grid.view.ui.general.TextLabel;
import com.games.halogen.puzzlePencil.sudoku.world.SudokuLayoutManager;

public class Cell extends SudokuObject {
    private IntPair coordinates;
    private TextLabel valueLabel;
    private Miniums miniums;

    private boolean isSelected;
    private boolean isEditable;
    private boolean isNumberValid;

    private Image bgImg;

    Cell(int r, int c){
        this.coordinates = new IntPair(r,c);
    }

    void setCoordinates(int i, int j) {
        this.coordinates.set(i,j);
        this.setPosition(this.coordinates.getSecond()*getWidth(), this.coordinates.getFirst()*getHeight());
    }

    @Override
    public void init() {
        PuzzlePencilInjector di = getCallbacks().getDependencyInjector();
        SudokuLayoutManager lm = getCallbacks().getLayoutManager();

        this.setSize(lm.cellSize + 2*lm.cellMargin, lm.cellSize + 2*lm.cellMargin);
        this.setPosition(this.coordinates.getSecond()*getWidth(), this.coordinates.getFirst()*getHeight());

        //set bg
        bgImg = new Image(di.getAssetManager().getSquareRegion());
        bgImg.setSize(lm.cellSize, lm.cellSize);
        bgImg.setPosition(lm.cellMargin, lm.cellMargin);
        bgImg.setColor(lm.deselectedCellColor);
        this.addActor(bgImg);

        //miniums
        this.miniums = new Miniums();
        miniums.setSize(lm.cellSize*lm.miniumsBlockRatio, lm.cellSize*lm.miniumsBlockRatio);
        miniums.setPosition((getWidth() - miniums.getWidth())/2, (getHeight() - miniums.getHeight())/2);
        addChildObject(this.miniums, false);

        //set text label
        valueLabel = new TextLabel("", di.getAssetManager().fontLabelStyle);
        valueLabel.setColor(lm.fontColor);
        valueLabel.setAlignment(0.5f,0.5f);
        valueLabel.setSize(bgImg.getWidth(), bgImg.getHeight());
        valueLabel.setPosition(bgImg.getX(), bgImg.getY());

        valueLabel.setTextHeight(lm.cellSize * lm.cellTextRatio);
        this.addActor(valueLabel);

        isSelected  = false;
        addUIListeners();
    }

    private void addUIListeners() {
        final Cell thisCell = this;
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getCallbacks().deselectCell();
                setActive(true);
                getCallbacks().selectCell(coordinates.getFirst(), coordinates.getSecond());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    void setActive(boolean isSelected) {
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
            miniums.setVisible(false);
        }
    }

    public int getValue() {
        if(valueLabel.getText().equals("")){
            return -1;
        }
        return Integer.parseInt(valueLabel.getText());
    }

    public void setEmpty() {
        valueLabel.setText("");
        miniums.setVisible(true);
    }

    public boolean isEmpty(){
        return valueLabel.getText().equals("");
    }

    public void setMiniums(Miniums miniums) {
        this.miniums.set(miniums);
    }

    public Miniums getMiniums(){
        return this.miniums;
    }

    public IntPair getCoordinates(){
        return coordinates;
    }

    public int getRow(){
        return coordinates.getFirst();
    }

    public int getColumn(){
        return coordinates.getSecond();
    }

    void setEditable(boolean editable){
        this.isEditable = editable;
        if(editable){
            valueLabel.setColor(getCallbacks().getLayoutManager().editableFontColor);
        }else {
            valueLabel.setColor(getCallbacks().getLayoutManager().fontColor);
        }
    }

    boolean isEditable() {
        return isEditable;
    }

    public void setValidity(boolean valid){
        this.isNumberValid = valid;
        if(this.isNumberValid){
            if(isEditable()) {
                this.valueLabel.setColor(getCallbacks().getLayoutManager().editableFontColor);
            }else{
                this.valueLabel.setColor(getCallbacks().getLayoutManager().fontColor);
            }
        }else{
            this.valueLabel.setColor(getCallbacks().getLayoutManager().errorFontColor);
        }
    }

    boolean isValid(){
        return isNumberValid;
    }

    void toggleMinium(int num) {
        miniums.toggleNum(num);
    }
}
