package com.games.halogen.puzzlePencil.sudoku.grid.view.grid;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.games.halogen.gameEngine.utils.Pair.IntPair;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.sudoku.grid.model.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.grid.view.SudokuObject;
import com.games.halogen.puzzlePencil.sudoku.grid.view.ui.general.TextLabel;
import com.games.halogen.puzzlePencil.sudoku.world.SudokuLayoutManager;

public class CellView extends SudokuObject {
    private IntPair coordinates;
    private TextLabel valueLabel;
    private MarksView marksView;

    private Image bgImg;

    CellView(int row, int col) {
        this.coordinates = new IntPair(row, col);
    }

    @Override
    public void init() {
        PuzzlePencilInjector di = getCallbacks().getDependencyInjector();
        SudokuLayoutManager lm = getCallbacks().getLayoutManager();

        //set bg
        bgImg = new Image(di.getAssetManager().getSquareRegion());
        bgImg.setSize(lm.cellSize, lm.cellSize);
        bgImg.setPosition(lm.cellMargin, lm.cellMargin);
        bgImg.setColor(lm.deselectedCellColor);
        this.addActor(bgImg);

        //miniums
        this.marksView = new MarksView();
        marksView.setSize(lm.cellSize * lm.miniumsBlockRatio, lm.cellSize * lm.miniumsBlockRatio);
        marksView.setPosition((getWidth() - marksView.getWidth()) / 2, (getHeight() - marksView.getHeight()) / 2);
        addChildObject(this.marksView, false);

        //set text label
        valueLabel = new TextLabel("", di.getAssetManager().fontLabelStyle);
        valueLabel.setColor(lm.fontColor);
        valueLabel.setAlignment(0.5f, 0.5f);
        valueLabel.setSize(bgImg.getWidth(), bgImg.getHeight());
        valueLabel.setPosition(bgImg.getX(), bgImg.getY());

        valueLabel.setTextHeight(lm.cellSize * lm.cellTextRatio);
        this.addActor(valueLabel);

        addUIListeners();
    }

    private void addUIListeners() {
        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getCallbacks().deselectCell();
                getCallbacks().selectCell(coordinates.getFirst(), coordinates.getSecond());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private void setEmpty() {
        valueLabel.setText("");
        marksView.setVisible(true);
    }

    void updateValue(Cell cell) {
        if (cell.hasValue()) {
            valueLabel.setText(cell.getValue());
            marksView.setVisible(false);
        } else {
            setEmpty();
        }
    }

    void updateMarks(Cell cell) {
        marksView.UpdateMarks(cell.getVisibleMarks());
    }

    void updateValueColor(Cell cell){
        if(cell.hasValidValue()){
            if(cell.isEditable()) {
                this.valueLabel.setColor(getCallbacks().getLayoutManager().editableFontColor);
            }else{
                this.valueLabel.setColor(getCallbacks().getLayoutManager().fontColor);
            }
        }else{
            this.valueLabel.setColor(getCallbacks().getLayoutManager().errorFontColor);
        }
    }

    void setHighlight(CellHighlight highlightType) {
        switch (highlightType) {
            case NONE:
                bgImg.setColor(getCallbacks().getLayoutManager().deselectedCellColor);
                break;
            case SELECTED:
                bgImg.setColor(getCallbacks().getLayoutManager().selectedCellColor);
                break;
            case HINT_TARGET:
                bgImg.setColor(getCallbacks().getLayoutManager().hintCellColor);
                break;
        }
    }
}
