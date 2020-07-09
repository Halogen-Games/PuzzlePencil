#ifdef GL_ES
precision highp float;
#endif

#define PI 3.14159265359
#define PI2 6.28318530718

uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

uniform vec2 u_resolution;
uniform float u_time;

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 vPosition;

//--------------------------------------------------------------------------------------------------
float plot(float y, vec2 uv){
    return smoothstep(y + 0.02, y, uv.y) * smoothstep(y - 0.02, y, uv.y);
}

vec2 noise2(vec2 uv){
    return fract(sin(uv * vec2(246.823, 842.265))*vec2(526.178, 429.248)) * 2. - 1.;
}

vec2 valNoise2(vec2 uv){
    vec2 id = floor(uv);
    vec2 f = fract(uv);

    vec2 a = noise2(id);
    vec2 b = noise2(a + vec2(1., 0.));
    vec2 c = noise2(a + vec2(0., 1.));
    vec2 d = noise2(a + vec2(1., 1.));

    vec2 r = mix(a, b, smoothstep(0.,1.,f.x));
    vec2 s = mix(c, d, smoothstep(0.,1.,f.x));

    return mix(r, s, smoothstep(0., 1., f.y));
}

vec2 fbm2(vec2 uv, float gain){
    float freq = 1.;
    vec2 val = vec2(0.);
    float amp = 1.;

    int numSteps = 7;
    for(int i=0; i<numSteps; i++){
        val += valNoise2(uv*freq) * amp;
        freq *= 2.;
        amp *= gain;
    }

    return val;
}

void main() {
    vec2 uv = (gl_FragCoord.xy - .5*u_resolution.xy) / u_resolution.y;
    vec4 color = vec4(vec3(0.),1.);

    float t = u_time;

    //reduce graph size
    float scale = 10.;
    uv *= scale;

    //fbm
    color.rg += noise2(floor(uv*2.));

    gl_FragColor = color;
}

