package com.games.halogen.gameEngine.renderer.gameArt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class ShapeCreator {
    private final PolygonSpriteBatch batch;
    private final Viewport viewport;

    //Shader
    private ShapeShader shapeShader;

    //Shape Properties
    private Color fillColor;
    private Color lineColor;
    private float lineThickness;

    public ShapeCreator(PolygonSpriteBatch batch, Viewport viewport){
        this.batch = batch;
        this.viewport = viewport;
    }

    public void init(){
        shapeShader = new ShapeShader(batch, viewport);
        shapeShader.init();

        setFillColor(new Color(0,0,0,1));
        setLineColor(new Color(0,0,0,1));
        setLineThickness(1);
    }

    public void setLineThickness(float thickness){
        this.lineThickness = thickness;
    }

    public void setFillColor(float r, float g, float b, float a){
        this.fillColor.set(r,g,b,a);
    }

    public void setFillColor(Color c){
        this.fillColor = c;
    }

    public void setLineColor(float r, float g, float b, float a){
        this.lineColor.set(r,g,b,a);
    }

    public void setLineColor(Color c){
        this.lineColor = c;
    }

    public void drawLine(float x1, float y1, float x2, float y2){
        Color currColor = batch.getColor();
        Color currFillColor = fillColor;

        batch.setColor(lineColor);
        setFillColor(lineColor);

        //todo: pool vector2
        Vector2 st = new Vector2(x1,y1);
        Vector2 en = new Vector2(x2,y2);
        Vector2 dir = en.sub(st);

        drawCircle(x1, y1, lineThickness/2);
        drawRotatedRect(x1,y1-lineThickness/2,dir.len(), lineThickness,0,lineThickness/2,dir.angle());
        drawCircle(x2, y2, lineThickness/2);

        batch.setColor(currColor);
        setFillColor(currFillColor);
    }

    public void drawRectLine(float x, float y, float width, float height){
        drawLine(x,y,x+width,y);
        drawLine(x+width,y,x+width,y+height);
        drawLine(x+width,y+height,x,y+width);
        drawLine(x,y+width,x,y);
    }

    public void drawRect(float x, float y, float width, float height){
        Color currColor = batch.getColor();
        batch.setColor(fillColor);
        batch.draw(shapeShader.getSquareFBO().getColorBufferTexture(), x, y, width, height);
        batch.setColor(currColor);
    }

    public void drawRotatedRect(float x, float y, float width, float height, float origX, float origY, float degrees){
        Color currColor = batch.getColor();
        batch.setColor(fillColor);
        TextureRegion sqTexReg = new TextureRegion(shapeShader.getSquareFBO().getColorBufferTexture());
        batch.draw(sqTexReg, x, y, origX, origY, width, height,1,1,degrees);
        batch.setColor(currColor);
    }

    public void drawCircleLine(float x, float y, float rad, int numSeg){
        ArrayList<Vector2> vertices = new ArrayList<>();
        for(int i=0; i<numSeg; i++){
           vertices.add(new Vector2(x + rad * MathUtils.cosDeg((i*360f)/numSeg), y + rad * MathUtils.sinDeg((i*360f)/numSeg)));
        }

        drawPolygonLine(vertices);
    }

    public void drawCircle(float x, float y, float rad){
        Color currColor = batch.getColor();
        batch.setColor(fillColor);
        batch.draw(shapeShader.getCircleFBO().getColorBufferTexture(), x-rad,y-rad,2*rad, 2*rad);
        batch.setColor(currColor);
    }

    public void drawPolygonLine(ArrayList<Vector2> vertices){
        if(vertices.size() < 3){
            throw new RuntimeException("Can't draw a polygon with less than 3 vertices");
        }

        for(int i=1; i < vertices.size(); i++){
            drawLine(vertices.get(i-1).x, vertices.get(i-1).y, vertices.get(i).x, vertices.get(i).y);
        }

        drawLine(vertices.get(vertices.size()-1).x,vertices.get(vertices.size()-1).y, vertices.get(0).x, vertices.get(0).y);
    }

    /**
     * Draws a polygon by drawing triangles in zigzag fashion
     * For ex, tr1(0,1,n-1) tr2(1,n-1,2) tr3(n-1,2,n-2) etc
     */
    public void drawPolygon(ArrayList<Vector2> vertices, boolean alternateTriangulation){
        if(vertices.size() < 3){
            throw new RuntimeException("Can't draw a polygon with less than 3 vertices");
        }

        float[] verts = new float[vertices.size() * 2];
        short[] triangles = new short[(vertices.size() - 2) * 3];

        if(alternateTriangulation) {
            //re-arrange vertices
            ArrayList<Vector2> arrangedVertices = new ArrayList<>();
            arrangedVertices.add(vertices.get(0));

            for (int i = 1; i <= vertices.size() / 2; i++) {
                arrangedVertices.add(vertices.get(i));
                if (i != vertices.size() - i) {
                    arrangedVertices.add(vertices.get(vertices.size() - i));
                }
            }

            vertices = arrangedVertices;
        }

        //create verts
        for(int i=0;i<vertices.size();i++){
            verts[i*2] = vertices.get(i).x;
            verts[i*2 + 1] = vertices.get(i).y;
        }

        //create triangles
        for(int i=0;i<vertices.size()-2;i++){
            triangles[i*3] = (short)i;
            triangles[i*3+1] = (short)(i+1);
            triangles[i*3+2] = (short)(i+2);
        }

        TextureRegion sqRegion = new TextureRegion(getRectFBO().getColorBufferTexture());

        PolygonRegion polygonRegion = new PolygonRegion(sqRegion,verts, triangles);

        batch.draw(polygonRegion,0,0);
    }
    public void drawPolygon(ArrayList<Vector2> vertices){
        drawPolygon(vertices, true);
    }

    public FrameBuffer getRectFBO(){
        return shapeShader.getSquareFBO();
    }
    public FrameBuffer getCircleFBO(){
        return shapeShader.getCircleFBO();
    }
    public FrameBuffer getGradientCircleFBO(){
        return shapeShader.getGradientCircleFBO();
    }

    public void dispose(){
        shapeShader.dispose();
    }
}
