#define PI 3.14159265359

uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

uniform vec2 u_resolution;
uniform float u_time;

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 vPosition;

vec2 coord;

vec3 circle(vec2 pos, float rad){
    return smoothstep(rad+0.01, rad,length(coord - pos));
}

void main() {
    gl_FragColor = vColor;
}

