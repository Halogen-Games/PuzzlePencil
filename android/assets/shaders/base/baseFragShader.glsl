#define PI 3.14159265359

uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 vPosition;

void main() {
    vec4 color = texture2D(u_texture, v_texCoords.x);
    gl_FragColor = color;
}

