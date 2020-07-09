package com.games.halogen.puzzlePencil.shader.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.games.halogen.gameEngine.renderer.shaders.primitives.ShadedBuffer;

public class DistortionBuffer extends ShadedBuffer {
    private float w;
    private float h;

    public DistortionBuffer(int width, int height, ShaderProgram program) {
        super(width, height, program);

        this.w = width;
        this.h = height;
    }

    @Override
    protected void drawToBuffer(Batch batch) {
        super.drawToBuffer(batch);
    }
}
