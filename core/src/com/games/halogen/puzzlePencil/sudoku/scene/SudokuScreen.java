package com.games.halogen.puzzlePencil.sudoku.scene;

import com.games.halogen.gameEngine.infra.SwitchScreenCallback;
import com.games.halogen.gameEngine.scene.GameScreen;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.sudoku.scene.world.SudokuWorld;
import com.games.halogen.puzzlePencil.sudoku.utils.SudokuUtils;

import java.util.ArrayList;
import java.util.Collections;

public class SudokuScreen extends GameScreen {

    public SudokuScreen(PuzzlePencilInjector di, SwitchScreenCallback switchScreenCallback){
        super(di, switchScreenCallback);
        setClearColor(1,1,1,1);
        setGameWorld(new SudokuWorld(di));
    }
}
