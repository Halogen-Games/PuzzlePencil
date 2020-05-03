package com.games.halogen.puzzlePencil.scene.view;

import com.games.halogen.gameEngine.scene.view.GameObject;
import com.games.halogen.puzzlePencil.scene.world.PuzzlePencilWorld;
import com.games.halogen.puzzlePencil.scene.world.PuzzlePencilWorldCallbacks;

public abstract class PuzzlePencilObject extends GameObject {
    protected PuzzlePencilWorldCallbacks getCallbacks(){
        return getGameWorld(PuzzlePencilWorld.class).getWorldCallbacks();
    }
}
