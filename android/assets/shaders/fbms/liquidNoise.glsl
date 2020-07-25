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

vec2 rand2(float x){
    return fract(sin(x*vec2(348.215, 961.234))*943.8478) * 2. - 1.;
}

vec2 interp(vec2 uv){
    return uv*uv*uv*(uv*(uv*6.-15.)+10.);
}

float noise2(vec2 uv){
    float a = dot(uv, vec2(124.218,328.13));
    return fract(sin(a)*246.256) * 2. - 1.;
}

float valNoise2(vec2 uv){
    vec2 id = floor(uv);
    vec2 f = fract(uv);

    float a = noise2(id);
    float b = noise2(id + vec2(1., 0.));
    float c = noise2(id + vec2(0., 1.));
    float d = noise2(id + vec2(1., 1.));

    vec2 sm = interp(f);

    float r = mix(a, b, sm.x);
    float s = mix(c, d, sm.x);

    return mix(r, s, sm.y);
}

float fbm2(vec2 uv, float freq, float gain){
    float val = 0.;
    float amp = 1.;

    int numSteps = 10;
    for(int i=0; i<numSteps; i++){
        val += valNoise2(uv*freq) * amp;
        freq *= 2.;
        amp *= gain;
    }

    return val*0.5+0.5;
}

void main() {
    vec2 uv = (gl_FragCoord.xy - .5*u_resolution.xy) / u_resolution.y;
    vec4 color = vec4(vec3(0.),1.);

    uv.y += 0.5;

    float t = u_time;

    //reduce graph size
    vec2 scale = vec2(5.);
    uv *= scale;


    uv.x += fbm2(uv, 1, 0.5);
    uv.y += fbm2(uv, 1, 0.5);

    t *= 0.2;

    //colors
    vec3 blue = vec3(0., 0.5, 1.);
    vec3 green = vec3(0., 1., 0.5);
    vec3 red = vec3(0.5, 0., 0.);
    vec3 cream = vec3(0.9,0.9,0.6);

    //fbm
    float val = fbm2(uv+t, 1, 0.5);
    color.rgb += mix(red, vec3(0.), smoothstep(0., 1., val*2.));
    color.rgb += mix(vec3(0.), cream, smoothstep(0., 1.,val*2.-1));

    gl_FragColor = color;
}

