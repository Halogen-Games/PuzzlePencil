package com.games.halogen.puzzlePencil.sudoku.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.scene.world.GameLayoutManager;
import com.games.halogen.puzzlePencil.PuzzlePencil;

public class SudokuLayoutManager extends GameLayoutManager {
    //Grid
    public float gridSize = PuzzlePencil.VIRTUAL_WIDTH * 0.95f;
    public Vector2 gridPos = new Vector2((PuzzlePencil.VIRTUAL_WIDTH - gridSize) / 2f, (PuzzlePencil.VIRTUAL_HEIGHT - gridSize) / 2f);

    //Cell
    public float cellMarginToSizeRatio = 0.05f;
    public float cellTextRatio = 1f;
    public float miniumsTextRatio = 0.4f;
    public float miniumsBlockRatio = 0.75f;             //% of cell space occupied by miniums square

    public float cellSize;
    public float cellMargin;

    //Next Button
    public Vector2 nextButtSize = new Vector2();
    public float nextButtTextHeightRatio = 0.75f;

    //colors
    public Color bgColor = new Color().set(0x121212ff);
    public Color gridLineColor = new Color().set(0x090909ff);
    public Color selectedCellColor = new Color().set(0x333333ff);;
    public Color deselectedCellColor = new Color().set(0x222222ff);
    public Color buttonBGColor = new Color().set(0x222222ff);;

    public Color editableFontColor = new Color().set(0xe9dec2ff);
    public Color fontColor = new Color().set(0x7c735aff);
    public Color errorFontColor = new Color().set(0xcf9889ff);
    public Color hintCellColor = new Color().set(0x00ff00ff);

    public float getGridLineThickness(){
        return cellSize * cellMarginToSizeRatio * 3f;
    }

    @Override
    public void updateLayout(GameDependencyInjector injector) {
        gridPos = new Vector2((injector.getViewport().getWorldWidth() - gridSize) / 2f, (injector.getViewport().getWorldHeight() - gridSize) / 1.5f);

        float nextButtAspectRatio = 4;
        float nextButtWidth = injector.getVirtualWidth()*0.4f;
        nextButtSize.set(nextButtWidth, nextButtWidth/nextButtAspectRatio);
    }
}
