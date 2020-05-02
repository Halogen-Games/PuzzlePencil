package com.games.halogen.puzzlePencil.sudoku;

import com.games.halogen.gameEngine.scene.GameScreen;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.sudoku.scene.world.SudokuWorld;

public class SudokuScreen extends GameScreen {

    public SudokuScreen(PuzzlePencilInjector di){
        super(di);
        setClearColor(1,1,1,1);
        setGameWorld(new SudokuWorld(di));
    }
}
