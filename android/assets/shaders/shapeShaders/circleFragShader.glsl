uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 v_position;

void main() {
    vec4 color = texture2D(u_texture, v_texCoords);

    vec2 tCoords = v_texCoords - .5;

    float dist = tCoords.x*tCoords.x + tCoords.y*tCoords.y;

    color.a = smoothstep(0.25,0.24,dist);

    gl_FragColor = color;
}

