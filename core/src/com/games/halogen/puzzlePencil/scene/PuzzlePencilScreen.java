package com.games.halogen.puzzlePencil.scene;

import com.games.halogen.gameEngine.infra.SwitchScreenCallback;
import com.games.halogen.gameEngine.scene.GameScreen;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.scene.world.PuzzlePencilWorld;

public class PuzzlePencilScreen extends GameScreen {

    public PuzzlePencilScreen(PuzzlePencilInjector di, SwitchScreenCallback switchScreenCallback){
        super(di, switchScreenCallback);
        setClearColor(1,1,1,1);
        setGameWorld(new PuzzlePencilWorld(di));
    }
}
