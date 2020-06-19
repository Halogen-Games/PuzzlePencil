package com.games.halogen.puzzlePencil.shader.shaderPrograms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TestShader extends ShaderProgram {
    public TestShader(){
        super(Gdx.files.internal("shaders/defaultShaders/defaultVertShader.glsl"),Gdx.files.internal("shaders/test/testFrag.glsl"));

        if(!this.isCompiled()){
            throw new GdxRuntimeException(this.getLog());
        }

        ShaderProgram.pedantic = false;
    }
}
