package com.games.halogen.puzzlePencil.shader.shaderPrograms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Mandelbrot extends ShaderProgram {
    public Mandelbrot(){
        super(Gdx.files.internal("shaders/fractals/mandelbrot/mandelbrotVertShader.glsl"),Gdx.files.internal("shaders/fractals/mandelbrot/mandelbrotFragShader.glsl"));

        if(!this.isCompiled()){
            throw new GdxRuntimeException(this.getLog());
        }

        ShaderProgram.pedantic = false;
    }
}
