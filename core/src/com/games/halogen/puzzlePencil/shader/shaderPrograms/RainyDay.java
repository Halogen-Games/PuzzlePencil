package com.games.halogen.puzzlePencil.shader.shaderPrograms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class RainyDay extends ShaderProgram {
    public RainyDay(){
        super(Gdx.files.internal("shaders/defaultShaders/defaultVertShader.glsl"),Gdx.files.internal("shaders/vertShaders/rainBuffer.glsl"));

        if(!this.isCompiled()){
            throw new GdxRuntimeException(this.getLog());
        }

        ShaderProgram.pedantic = false;
    }
}
