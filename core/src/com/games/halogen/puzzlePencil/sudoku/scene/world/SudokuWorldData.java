package com.games.halogen.puzzlePencil.sudoku.scene.world;

import com.games.halogen.gameEngine.scene.world.GameWorldData;

public class SudokuWorldData extends GameWorldData {
    public int numBlocks = 3;
    public int level = 1;
    public int numRows;

    public SudokuWorldData(){
        numRows = numBlocks * numBlocks;
    }
}
