package com.games.halogen.puzzlePencil.shader;

import com.games.halogen.gameEngine.infra.SwitchScreenCallback;
import com.games.halogen.gameEngine.scene.GameScreen;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.shader.world.ShaderWorld;

public class ShaderScreen extends GameScreen {

    public ShaderScreen(PuzzlePencilInjector di, SwitchScreenCallback switchScreenCallback){
        super(di, switchScreenCallback);
        setClearColor(0,0,0,1);
        setGameWorld(new ShaderWorld(di));
    }
}
