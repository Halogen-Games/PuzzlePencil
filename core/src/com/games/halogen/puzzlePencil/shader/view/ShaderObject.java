package com.games.halogen.puzzlePencil.shader.view;

import com.games.halogen.gameEngine.scene.view.GameObject;
import com.games.halogen.puzzlePencil.shader.world.ShaderWorld;
import com.games.halogen.puzzlePencil.shader.world.ShaderWorldCallbacks;

public abstract class ShaderObject extends GameObject {
    protected ShaderWorldCallbacks getCallbacks(){
        return getGameWorld(ShaderWorld.class).getWorldCallbacks();
    }
}
