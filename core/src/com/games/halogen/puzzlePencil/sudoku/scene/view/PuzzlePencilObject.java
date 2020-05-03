package com.games.halogen.puzzlePencil.sudoku.scene.view;

import com.games.halogen.gameEngine.scene.view.GameObject;
import com.games.halogen.puzzlePencil.sudoku.scene.world.SudokuWorldCallbacks;

public abstract class PuzzlePencilObject extends GameObject {
    protected SudokuWorldCallbacks getCallbacks(){
        return getGameWorldCallbacks(SudokuWorldCallbacks.class);
    }
}
