package com.games.halogen.gameEngine.renderer.rayMarcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.games.halogen.gameEngine.renderer.shaders.primitives.ShadedBuffer;
import com.games.halogen.gameEngine.scene.view.GameObject;

import java.util.Locale;

public abstract class RayMarchedScene extends GameObject {
    private ShadedBuffer buffer;

    private String sceneSDF;
    private String sceneSDFContent;

    public RayMarchedScene(int width, int height){
        buffer = new ShadedBuffer(width, height, createShader());
    }

    public abstract void setupScene();

    /*
    Primitives Functions
     */
    protected void addSphere(Vector3 pos,float radius){
        String sphereCode = "    Sphere s;\n" +
                "    s.center = vec3(%f, %f, %f);\n" +
                "    s.radius = %f;\n" +
                "    d = min(d, distSphere(p, s));\n";

        sceneSDFContent += String.format(Locale.US, sphereCode, pos.x, pos.y, pos.z, radius);
    }

    protected void addCapsule(Vector3 a, Vector3 b, float radius){
        String capsuleCode = "Capsule c;\n" +
                "    c.a = vec3(%f, %f, %f);\n" +
                "    c.b = vec3(%f, %f, %f);\n" +
                "    c.radius = %f;\n" +
                "    d = min(d, distCapsule(p, c));\n";

        sceneSDFContent += String.format(Locale.US, capsuleCode, a.x, a.y, a.z, b.x, b.y, b.z, radius);
    }

    /*
    Overridden Functions
    */
    @Override
    public void init() {
        this.addChildObject(buffer);
    }

    @Override
    public void layout() {
        super.layout();
        this.buffer.setSize(getWidth(), getHeight());
    }

    /*
            Private Functions
             */
    private ShaderProgram createShader() {
        String vertShader = Gdx.files.internal("shaders/rayMarcher/rayMarchVert.glsl").readString();
        String fragShader = generateFragShader();

        ShaderProgram rv = new ShaderProgram(vertShader, fragShader);

        if(!rv.isCompiled()){
            throw new GdxRuntimeException(rv.getLog());
        }

        ShaderProgram.pedantic = false;

        return rv;
    }

    private String generateFragShader() {
        String prefix = Gdx.files.internal("shaders/rayMarcher/rayMarchFragPrefix.glsl").readString();

        updateSceneSDF();
        return prefix + sceneSDF;
    }

    private void updateSceneSDF(){
        sceneSDF = "";
        sceneSDFContent = "";
        setupScene();

        sceneSDF += "float distScene(vec3 p){\n" +
                    "float d = distXZ(p);\n" +
                    "\n";
        sceneSDF += sceneSDFContent;
        sceneSDF += "return d;\n}";
    }
}
