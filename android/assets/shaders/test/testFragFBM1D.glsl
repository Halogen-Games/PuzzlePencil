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

float noise(float x){
    return fract(sin(x * 246.852)*953.158) * 2. - 1.;
}

float valNoise(float x){
    float id = floor(x);
    float f = fract(x);
    return mix(noise(id), noise(id+1.), smoothstep(0., 1., f));
}

float sinWave(float x){
    return sin(x * PI2);
}

float fbm(float x, float gain){
    float freq = 1.;
    float val = 0.;
    float amp = 1.;

    int numSteps = 7;
    for(int i=0; i<numSteps; i++){
        val += valNoise(x*freq) * amp;
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
    vec2 scale = vec2(10.,20.);
    uv *= scale;

    //axis
    color.g += plot(0., uv);
    color.g += smoothstep(-0.01, 0.0, uv.x) * smoothstep(0.01, 0.0, uv.x);

    //fbm
    float val = fbm((uv.x + t)*1., 0.5);
    color.rg += vec2(plot(val, uv));

    gl_FragColor = color;
}

