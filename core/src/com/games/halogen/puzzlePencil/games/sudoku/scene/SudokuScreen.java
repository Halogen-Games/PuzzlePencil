package com.games.halogen.puzzlePencil.games.sudoku.scene;

import com.games.halogen.gameEngine.infra.SwitchScreenCallback;
import com.games.halogen.gameEngine.scene.GameScreen;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.games.sudoku.scene.world.SudokuWorld;

public class SudokuScreen extends GameScreen {

    public SudokuScreen(PuzzlePencilInjector di, SwitchScreenCallback switchScreenCallback){
        super(di, switchScreenCallback);
        setClearColor(1,1,1,1);
        setGameWorld(new SudokuWorld(di));
    }
}
