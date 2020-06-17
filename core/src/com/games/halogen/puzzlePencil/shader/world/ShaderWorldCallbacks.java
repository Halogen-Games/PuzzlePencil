package com.games.halogen.puzzlePencil.shader.world;

import com.games.halogen.gameEngine.scene.world.GameWorldCallbacks;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;

public class ShaderWorldCallbacks extends GameWorldCallbacks {
    ShaderWorldCallbacks(ShaderWorld world){
        super(world);
    }

    public PuzzlePencilInjector getDependencyInjector() {
        return getGameDependencyInjector(PuzzlePencilInjector.class);
    }

    public ShaderWorld getWorld(){
        return getGameWorld(ShaderWorld.class);
    }
}
