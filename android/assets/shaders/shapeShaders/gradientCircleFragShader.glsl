uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 v_position;

void main() {
    vec4 color = texture2D(u_texture, v_texCoords);

    vec2 tCoords = v_texCoords - .5;

    float dist = tCoords.x*tCoords.x + tCoords.y*tCoords.y;

    /*
    ideally the value below should be 0.25,
    but using 0.315 by trial and error as edges feather out into transparency on using 0.25
    */
    color.a = smoothstep(0.315,0.0,dist);

    gl_FragColor = color;
}

