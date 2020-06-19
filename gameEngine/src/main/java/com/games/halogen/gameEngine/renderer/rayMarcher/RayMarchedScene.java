package com.games.halogen.gameEngine.renderer.rayMarcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.games.halogen.gameEngine.renderer.shaders.primitives.ShadedBuffer;
import com.games.halogen.gameEngine.scene.view.GameObject;

import java.util.Locale;

public abstract class RayMarchedScene extends GameObject {
    private ShadedBuffer buffer;

    private String sceneSDFFunc;
    private String sceneSDF;

    public RayMarchedScene(int width, int height){
        buffer = new ShadedBuffer(width, height, createShader());
    }

    public abstract String setupScene();

    /*
    Primitives Functions
     */
    protected String sphere(Vector3 pos, float radius){
        String sphereCode = "distSphere(p, vec3(%f, %f, %f), %f)\n";
        return String.format(Locale.US, sphereCode, pos.x, pos.y, pos.z, radius);
    }

    protected void addCapsule(Vector3 a, Vector3 b, float radius){
        String capsuleCode = "Capsule c;\n" +
                "    c.a = vec3(%f, %f, %f);\n" +
                "    c.b = vec3(%f, %f, %f);\n" +
                "    c.radius = %f;\n" +
                "    d = min(d, distCapsule(p, c));\n";

        sceneSDF += String.format(Locale.US, capsuleCode, a.x, a.y, a.z, b.x, b.y, b.z, radius);
    }

    /*
    Operation Functions
     */
    protected String add(String sdf1, String sdf2){
        return String.format(Locale.US,"min(%s, %s)", sdf1, sdf2);
    }

    protected String mix(String sdf1, String sdf2, String mix){
        return String.format(Locale.US,"mix(%s, %s, %s)", sdf1, sdf2, mix);
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
    getters
     */
    protected String getSceneSDF(){
        return sceneSDF;
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
        return prefix + sceneSDFFunc;
    }

    private void updateSceneSDF(){
        sceneSDFFunc = "";
        sceneSDF = "d";

        sceneSDFFunc += "float distScene(vec3 p){\n" +
                    "float d = distXZ(p);\n" +
                    "\n";

        sceneSDFFunc += String.format("return %s;\n}", setupScene());
    }
}
