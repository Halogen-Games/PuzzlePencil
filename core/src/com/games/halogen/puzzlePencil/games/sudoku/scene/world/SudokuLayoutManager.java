package com.games.halogen.puzzlePencil.games.sudoku.scene.world;

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
    public float cellSize;

    //Next Button
    public Vector2 nextButtSize = new Vector2();
    public float nextButtTextHeightRatio = 0.75f;

    //colors
    public Color bgColor = new Color().set(0xffffffff);
    public Color gridLineColor = new Color().set(0x555555ff);
    public Color deselectedCellColor = new Color().set(0xccccccff);
    public Color fontColor = new Color().set(0x000000ff);;
    public Color buttonBGColor = new Color().set(0x999999ff);;
    public Color selectedCellColor = new Color().set(0x999999ff);;

    public float getGridLineThickness(){
        return cellSize * cellMarginToSizeRatio * 2f;
    }

    @Override
    public void updateLayout(GameDependencyInjector injector) {
        gridPos = new Vector2((injector.getViewport().getWorldWidth() - gridSize) / 2f, (injector.getViewport().getWorldHeight()- gridSize) / 2f);

        float nextButtAspectRatio = 4;
        float nextButtWidth = injector.getVirtualWidth()*0.4f;
        nextButtSize.set(nextButtWidth, nextButtWidth/nextButtAspectRatio);
    }
}
