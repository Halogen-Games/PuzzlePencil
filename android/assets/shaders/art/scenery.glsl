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
float rose(float r, float th){
    r *= 0.5;
    float rv = 0.;
    rv += fract(th + r);
    rv -= fract(th - r);
    rv += fract(th + r*2 + 0.5);
    rv -= fract(th - r*3 - 0.5);

    rv *= smoothstep(3., 2.99, r*2);

    return rv;
}

float psin(float a){
    return sin(a)*0.5+0.5;
}

vec2 psin(vec2 v){
    return sin(v)*0.5+0.5;
}

vec4 sun(vec2 uv, float t, float rad){
    vec4 rv = vec4(0.);

    float r = length(uv);
    float theta = atan2(uv.y, uv.x)/PI2 + 0.5;

    rv.r = 1.;
    rv.g = 1.;

    rv.b = fract(theta + psin(r*20/rad + t));
    rv.b *= smoothstep(1.,0.5, rv.b);

    rv.b = 1. - rv.b;
    rv.b*= 0.7;

    rv.a = smoothstep(rad, rad*.5, r);

    return rv;
}

vec2 getSunDist(vec2 uv, float t){
    return vec2(sin(t * 5. + uv.y*70.) * 0.005, 0.);
}

float sinMountain(vec2 uv, float t, float freq, float speed, float height){
    float bound = sin(uv.x*freq + t*speed)*height;
    return smoothstep(bound, bound - 0.001, uv.y);
}

void main() {
    vec2 uv = (gl_FragCoord.xy - .5*u_resolution.xy) / u_resolution.y;
    vec4 color = vec4(vec3(0.),1.);
    float t = u_time;

    //sky blue
    color.rgb = vec3(0.53,0.8,0.92);

    //mountains
    float p = sinMountain(uv, t, 11., 0.2, 0.1);
    color.rgb = mix(color.rgb, vec3(0.1, 0.5, 0.2), p);

    p = sinMountain(uv + vec2(0., 0.05), t, 7., 0.5, 0.1);
    color.rgb = mix(color.rgb, vec3(0.1, 0.8, 0.2), p);

    p = sinMountain(uv + vec2(0., 0.2), t, 3., 1., 0.02);
    color.rgb = mix(color.rgb, vec3(0.7, 0.55, 0.2), p);


    //sun
    vec2 sunPos = vec2(-0.5, 0.25);

    vec4 sunColor = sun(uv - sunPos + getSunDist(uv - sunPos, t), t, 0.15);
    color.rgb = mix(color.rgb, sunColor.rgb, sunColor.a);

//    color.rgb = vec3(0.);
//    color.rg = dUV;


    gl_FragColor = color;
}

