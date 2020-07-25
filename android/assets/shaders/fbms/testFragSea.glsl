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

    float totalAmp = 0.;

    int numSteps = 10;
    for(int i=0; i<numSteps; i++){
        totalAmp += amp;
        val += valNoise2(uv*freq) * amp;
        freq *= 2.;
        amp *= gain;
    }

    return val*0.5/totalAmp+0.5;
}

vec3 getColorSpike(vec3 c1, vec3 c2, float st, float en, float val){
    float strength = step(st, val);
    strength *= step(-en, -val);

    return strength*mix(c1, c2, smoothstep(0., 1., (val - st) / (en - st)));
}

void main() {
    vec2 uv = (gl_FragCoord.xy - .5*u_resolution.xy) / u_resolution.y;
    vec4 color = vec4(vec3(0.),1.);

    float t = u_time;

    t *= 0.5;

    uv *= 2.;
    uv.x += fbm2(uv + vec2(t, 0.)*0.2, 1, 0.5)*0.4;
    uv.y += fbm2(uv.yx + vec2(0,t)*0.2, 1, 0.5)*0.4;

    uv *= 3.;
    //colors
    vec3 lightBlue = vec3(120., 223., 255.)/255.;
    vec3 blue = vec3(0., 64., 120.)/255.;
    vec3 darkBlue = vec3(0., 14., 26.)/255.;
    vec3 green = vec3(21., 54., 0.)/255.;
    vec3 red = vec3(0.5, 0., 0.);
    vec3 cream = vec3(0.9,0.9,0.6);
    vec3 brown = vec3(46., 22., 0.)/255.;

    //fbm
    float val = fbm2(uv+vec2(0., 0.), 0.5, 0.45);
    color.rgb += getColorSpike(brown * 5., brown*1.5, 0., 0.22, val);
    color.rgb += getColorSpike(brown*1.5, brown, 0.22, 0.25, val);
    color.rgb += getColorSpike(brown, lightBlue, 0.25, 0.3, val);
    color.rgb += getColorSpike(lightBlue, blue, 0.3, 0.5, val);
    color.rgb += getColorSpike(blue, blue*0.5, 0.5, .7, val);
    color.rgb += getColorSpike(blue*0.5, darkBlue, 0.7, 1., val);

    color *= 1.5;

    gl_FragColor = color;
}

