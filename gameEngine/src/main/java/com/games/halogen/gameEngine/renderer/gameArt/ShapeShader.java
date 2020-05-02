package com.games.halogen.gameEngine.renderer.gameArt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;

class ShapeShader {
    private final PolygonSpriteBatch batch;
    private final Viewport vp;

    //buffers
    private int bufferSize;
    private HashMap<String, FrameBuffer> shapeFBOs;

    //programs
    private HashMap<String, ShaderProgram> shaderPrograms;

    ShapeShader(PolygonSpriteBatch batch, Viewport vp){
        this.batch = batch;
        this.vp = vp;
    }

    void init(){
        bufferSize = 256;
        shapeFBOs = new HashMap<>();

        //program creation is slow because file reading is slow
        shaderPrograms = new HashMap<>();
    }

    private void resetBatchAndVP(){
        batch.setShader(null);
        vp.apply();
    }

    private String getShader(String path){
        return Gdx.files.internal(path).readString();
    }

    private ShaderProgram createProgram(String prefix){
        ShaderProgram program = new ShaderProgram(getShader("shaders/shapeShaders/shapeVertShader.glsl"), getShader("shaders/shapeShaders/"+prefix+"FragShader.glsl"));
        if (!program.isCompiled()) {
            throw new GdxRuntimeException(program.getLog());
        }
        return program;
    }

    private ShaderProgram getProgram(String programName){
        if(!shaderPrograms.containsKey(programName)){
            shaderPrograms.put(programName, createProgram(programName));
        }
        return shaderPrograms.get(programName);
    }

    private void clearFrameBuffer(FrameBuffer buffer){
        clearFrameBuffer(buffer, new Color(1,1,1,0));
    }

    private void clearFrameBuffer(FrameBuffer buffer, Color color){
        batch.flush();
        buffer.begin();
        Gdx.gl.glClearColor(color.r,color.g,color.b,color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.flush();
        buffer.end();
        vp.apply();
    }

    private FrameBuffer createCircleFBO(){
        //set shader on batch
        batch.setShader(getProgram("circle"));

        //create and clear buffer
        FrameBuffer bufferA = new FrameBuffer(Pixmap.Format.RGBA8888,bufferSize,bufferSize,false);
        FrameBuffer bufferB = new FrameBuffer(Pixmap.Format.RGBA8888,bufferSize,bufferSize,false);
        clearFrameBuffer(bufferA);
        clearFrameBuffer(bufferB, new Color(1,1,1,1));

        //draw in buffer (drawing clean buffer into itself modulating it with shader)
        bufferA.begin();
        batch.draw(bufferB.getColorBufferTexture(),0,0, vp.getWorldWidth(), vp.getWorldHeight());
        batch.flush();
        bufferA.end();

        //reset shader and VP
        resetBatchAndVP();

        bufferB.dispose();
        return bufferA;
    }

    FrameBuffer getCircleFBO(){
        if(!shapeFBOs.containsKey("circle")){
            shapeFBOs.put("circle", createCircleFBO());
        }
        return shapeFBOs.get("circle");
    }

    private FrameBuffer createGradientCircleFBO(){
        //set shader on batch
        batch.setShader(getProgram("gradientCircle"));

        //create and clear buffer
        FrameBuffer bufferA = new FrameBuffer(Pixmap.Format.RGBA8888,bufferSize,bufferSize,false);
        FrameBuffer bufferB = new FrameBuffer(Pixmap.Format.RGBA8888,bufferSize,bufferSize,false);
        clearFrameBuffer(bufferA);
        clearFrameBuffer(bufferB, new Color(1,1,1,1));

        //draw in buffer (drawing clean buffer into itself modulating it with shader)
        bufferA.begin();
        batch.draw(bufferB.getColorBufferTexture(),0,0, vp.getWorldWidth(), vp.getWorldHeight());
        batch.flush();
        bufferA.end();

        //reset shader and VP
        resetBatchAndVP();

        bufferB.dispose();
        return bufferA;
    }

    FrameBuffer getGradientCircleFBO(){
        if(!shapeFBOs.containsKey("gradientCircle")){
            shapeFBOs.put("gradientCircle", createGradientCircleFBO());
        }
        return shapeFBOs.get("gradientCircle");
    }

    private FrameBuffer createSquareFBO(){
        //create and clear buffer
        FrameBuffer bufferA = new FrameBuffer(Pixmap.Format.RGBA8888,bufferSize,bufferSize,false);
        clearFrameBuffer(bufferA,Color.WHITE);
        return bufferA;
    }

    FrameBuffer getSquareFBO(){
        if(!shapeFBOs.containsKey("square")){
            shapeFBOs.put("square", createSquareFBO());
        }
        return shapeFBOs.get("square");
    }

    void dispose(){
        //dispose shader programs
        for (Map.Entry mapElement : shaderPrograms.entrySet()) {
            String key = (String)mapElement.getKey();
            shaderPrograms.get(key).dispose();
            System.out.println("Disposed Shader Program:" + key);
        }

        //dispose FBOs
        for (Map.Entry mapElement : shapeFBOs.entrySet()) {
            String key = (String)mapElement.getKey();
            shapeFBOs.get(key).dispose();
            System.out.println("Disposed Frame Buffer:" + key);
        }
    }
}
