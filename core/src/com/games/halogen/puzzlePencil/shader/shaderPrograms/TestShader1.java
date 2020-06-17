package com.games.halogen.puzzlePencil.shader.shaderPrograms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TestShader1 extends ShaderProgram {
    public TestShader1(){
        super(Gdx.files.internal("shaders/testShaders/testVertShader.glsl"),Gdx.files.internal("shaders/testShaders/testFragShader.glsl"));

        if(!this.isCompiled()){
            throw new GdxRuntimeException(this.getLog());
        }

        ShaderProgram.pedantic = false;
    }
}
