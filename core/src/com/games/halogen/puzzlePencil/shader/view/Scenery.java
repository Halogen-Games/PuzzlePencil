package com.games.halogen.puzzlePencil.shader.view;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.games.halogen.gameEngine.renderer.rayMarcher.RayMarchedScene;

public class Scenery extends RayMarchedScene {
    public Scenery(int width, int height) {
        super(width, height);
    }

    @Override
    public String setupScene() {
        String s = getSceneSDF();
        String s2 = this.mix(sphere(new Vector3(0,1,0), 1),sphere(new Vector3(1,0.5f,0), 0.5f), "sin(u_time)*0.5+0.5");
        s = this.add(s,s2);

        return s;

    }
}
