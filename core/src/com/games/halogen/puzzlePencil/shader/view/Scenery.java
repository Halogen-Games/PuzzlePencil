package com.games.halogen.puzzlePencil.shader.view;

import com.badlogic.gdx.math.Vector3;
import com.games.halogen.gameEngine.renderer.rayMarcher.RayMarchedScene;

public class Scenery extends RayMarchedScene {
    public Scenery(int width, int height) {
        super(width, height);
    }

    @Override
    public void setupScene() {
        this.addSphere(new Vector3(), 1);
        this.addCapsule(new Vector3(1,2,3), new Vector3(2,1,-2), 0.5f);
    }
}
