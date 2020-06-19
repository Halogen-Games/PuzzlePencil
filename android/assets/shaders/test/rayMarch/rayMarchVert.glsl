//incoming Position attribute from our SpriteBatch
attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;

//"out" varyings to our fragment shader
varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 v_position;

void main() {
    vColor = a_color;
    v_texCoords = a_texCoord0;

    //transform our 2D screen space position into 3D world space
    v_position = a_position;
    gl_Position = u_projTrans * a_position;
}