package com.games.halogen.puzzlePencil.infra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.games.halogen.gameEngine.infra.GameDependencyInjector;
import com.games.halogen.gameEngine.infra.GameLayoutManager;

public class PuzzlePencilLayoutManager extends GameLayoutManager {
    //Grid
    public float gridSize = GameData.VIRTUAL_WIDTH * 0.95f;
    public Vector2 gridPos = new Vector2((GameData.VIRTUAL_WIDTH - gridSize) / 2f, (GameData.VIRTUAL_HEIGHT - gridSize) / 2f);

    //Cell
    public float cellMarginToSizeRatio = 0.05f;
    public float cellTextRatio = 1f;
    public float cellSize;

    //Next Button
    public Vector2 nextButtSize = new Vector2();
    public float nextButtTextHeightRatio = 0.75f;

    //colors
    public Color bgColor = new Color().set(0xcffee2ff);
    public Color gridLineColor = new Color().set(0x343e3dff);
    public Color cellColor = new Color().set(0x607466ff);
    public Color fontColor = bgColor;
    public Color buttonBGColor = gridLineColor;

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
