package com.games.halogen.puzzlePencil.shader.shaderPrograms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class RayMarchingTut extends ShaderProgram {
    public RayMarchingTut(){
        super(Gdx.files.internal("shaders/test/rayMarch/rayMarchVert.glsl"),Gdx.files.internal("shaders/teat/rayMarch/rayMarchFrag.glsl"));

        if(!this.isCompiled()){
            throw new GdxRuntimeException(this.getLog());
        }

        ShaderProgram.pedantic = false;
    }
}
