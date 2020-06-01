package com.games.halogen.puzzlePencil.sudoku.viewOld;

import com.games.halogen.gameEngine.scene.view.GameObject;
import com.games.halogen.puzzlePencil.sudoku.world.SudokuWorld;
import com.games.halogen.puzzlePencil.sudoku.world.SudokuWorldCallbacks;

public abstract class SudokuObject extends GameObject {
    protected SudokuWorldCallbacks getCallbacks(){
        return getGameWorld(SudokuWorld.class).getCallbacks();
    }
}
