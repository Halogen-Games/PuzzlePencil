package com.games.halogen.puzzlePencil.sudoku.world;

import com.games.halogen.gameEngine.scene.world.GameWorldData;

public class SudokuWorldData extends GameWorldData {
    public int gridDimensions = 9;
    public int level = 5;
    public int numRows;

    public SudokuWorldData(){
        numRows = gridDimensions * gridDimensions;
    }

    public void levelPlus(){
        level++;
        if(level > 4){
            level = 4;
        }
    }
}
