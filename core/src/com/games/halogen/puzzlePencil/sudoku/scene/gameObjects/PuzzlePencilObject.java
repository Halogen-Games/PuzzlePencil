package com.games.halogen.puzzlePencil.sudoku.scene.gameObjects;

import com.games.halogen.gameEngine.scene.world.gameObjects.GameObject;
import com.games.halogen.puzzlePencil.sudoku.scene.world.SudokuWorldCallbacks;

public abstract class PuzzlePencilObject extends GameObject {
    protected SudokuWorldCallbacks getCallbacks(){
        return getGameWorldCallbacks(SudokuWorldCallbacks.class);
    }
}
