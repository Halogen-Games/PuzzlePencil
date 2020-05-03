package com.games.halogen.puzzlePencil.scene.world;

import com.games.halogen.gameEngine.scene.world.GameWorldCallbacks;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;

public class PuzzlePencilWorldCallbacks extends GameWorldCallbacks {
    PuzzlePencilWorldCallbacks(PuzzlePencilWorld world){
        super(world);
    }

    public PuzzlePencilInjector getDependencyInjector() {
        return getGameDependencyInjector(PuzzlePencilInjector.class);
    }

    public PuzzlePencilWorld getWorld(){
        return getGameWorld(PuzzlePencilWorld.class);
    }
}
