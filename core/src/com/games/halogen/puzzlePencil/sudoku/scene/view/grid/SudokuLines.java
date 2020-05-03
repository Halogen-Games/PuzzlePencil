package com.games.halogen.puzzlePencil.sudoku.scene.view.grid;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilLayoutManager;
import com.games.halogen.puzzlePencil.sudoku.scene.view.PuzzlePencilObject;

class SudokuLines extends PuzzlePencilObject {
    private PuzzlePencilLayoutManager lm;

    @Override
    public void init() {
        lm = getCallbacks().getDependencyInjector().getLayoutManager();

        Image leftLine = getLineImage();
        this.addActor(leftLine);

        Image rightLine = getLineImage();
        rightLine.moveBy(lm.gridSize,0);
        this.addActor(rightLine);

        Image bottomLine = getLineImage();
        bottomLine.rotateBy(-90);
        this.addActor(bottomLine);

        Image topLine = getLineImage();
        topLine.rotateBy(-90);
        topLine.moveBy(0,lm.gridSize);
        this.addActor(topLine);

        //internal vertical lines
        int numBlocks = getCallbacks().getDependencyInjector().sudokuData.numBlocks;
        for(int i=1;i<numBlocks;i++){
            Image line = getLineImage();
            line.moveBy(i*lm.gridSize/numBlocks, 0);
            this.addActor(line);
        }

        //internal horizontal lines
        for(int i=1;i<numBlocks;i++){
            Image line = getLineImage();
            line.rotateBy(-90);
            line.moveBy(0,i*lm.gridSize/numBlocks);
            this.addActor(line);
        }
    }

    @Override
    public void modelUpdated() {
        //todo: fill
    }

    private Image getLineImage(){
        Image rv = new Image(getCallbacks().getDependencyInjector().getAssetManager().getSquareRegion());

        float lineThickness = lm.getGridLineThickness();

        rv.setColor(lm.gridLineColor);
        rv.setPosition(-lineThickness/2,-lineThickness/2);
        rv.setSize(lineThickness, lm.gridSize + lineThickness);
        rv.setOrigin(lineThickness/2,lineThickness/2);

        return rv;
    }
}
