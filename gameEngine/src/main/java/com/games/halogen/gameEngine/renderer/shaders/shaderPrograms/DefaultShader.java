package com.games.halogen.gameEngine.renderer.shaders.shaderPrograms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class DefaultShader extends ShaderProgram {

    public DefaultShader(){
        super(Gdx.files.internal("shaders/defaultShaders/defaultVertShader.glsl"),Gdx.files.internal("shaders/defaultShaders/defaultFragShader.glsl"));

        if(!this.isCompiled()){
            throw new GdxRuntimeException(this.getLog());
        }

        ShaderProgram.pedantic = false;
    }
}
