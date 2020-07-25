#ifdef GL_ES
precision highp float;
#endif

#define PI 3.14159265359
#define PI2 3.14159265359 * 2.

uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

uniform vec2 u_resolution;
uniform float u_time;

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 vPosition;

//--------------------------------------------------------------------------------------------------
float noise(float t){
    return fract(sin(t*3456.)*6547.);
}

float noise(vec2 v){
    return fract(sin(v.x*73.14 + v.y*454.532)*543.124);
}

vec4 noise14(float t){
    return fract(sin(t*vec4(123., 1024., 3456., 9564.))*vec4(6547., 345., 8799., 1564.));
}

float posSin(float a){
    return sin(a)*0.5+0.5;
}

float posCos(float a){
    return cos(a)*0.5+0.5;
}

mat2 rotMat(float a){
    a *= PI2 / 180.;
    return mat2(cos(a), -sin(a), sin(a), cos(a));
}

/*
returns an offset creating rain drops effect
*/
vec2 rain(vec2 uv, float t, float speed){
    t *= speed;

    //change coordSys aspect ratio
    vec2 a = vec2(2.,1.);
    vec2 st = uv*a;

    //divide coordSys in bands of width 1 and move by random offset
    st.y += t*0.22 + noise(floor(st.x));

    //get coordinate id by flooring
    vec2 id = floor(st);

    //convert coord sys into blocks going from -0.5 to 0.5
    st = fract(st) - 0.5;

    //randomly offset each block's animation in time
    t += noise(id)*PI2;

    //get point on path of main drop in this block
    float x = sin(uv.y*((noise(id.x))*PI/8. + PI/8.) + noise(id.x)*2.*PI)*0.35;
    x = 0.;
    float y = -sin(t+sin(t+sin(t)*.5)) * 0.43;

    vec2 p1 = vec2(x, y);
    vec2 offset1 = (st-p1)/a;
    float d = length(offset1);
    float m1 = smoothstep(0.06,0.0, d);

    vec2 offset2 = (fract(uv*a.x*vec2(1.,2.))-0.5)/vec2(1.,2.) - vec2(p1.x,0.);
    d = length(offset2);
    float m2 = smoothstep(0.15*(0.5 - st.y),0., d) * smoothstep(0., .1, st.y - p1.y);

    if(st.x > .46 || st.y > 0.49){
        m1 = 1.;
    }
    return vec2(m1*offset1*30.+m2*offset2*10.);
}


//**************************************************************************************************
vec2 waterDistort(vec2 uv, float t){
    if(uv.y>0){
        return vec2(0.);
    }

    uv *= 100.;

    return vec2(sin(uv.x), sin(uv.x));
}

vec2 getLayerDistortion(vec2 uv, float t){
    vec2 d = vec2(0.);

//    d += vec2(noise(uv + t), noise(uv - t))*0.002;

    d += waterDistort(uv, t);

    return d;
}

float getVignette(vec2 uv, float strength, float aspectRatio){
    uv.x *= aspectRatio;
    uv *= 2.;

    float a = smoothstep(1.,1. - strength,abs(uv.x));
    float b = smoothstep(1.,1. - strength,abs(uv.y));

    return a*b;
}

float star(vec2 uv, vec2 p, vec2 sz, float t){
    uv *= 2.;
    uv -= p;
    float r = length(uv);
    float a = atan2(uv.y, uv.x);

    return smoothstep(sz.y, sz.x, r*(1.+2.*posSin(a*5.)));
}

float galazy(vec2 uv, vec2 p, vec2 sz, float t){
    uv *= 2.;
    uv -= p;
    float r = length(uv);
    float a = atan2(uv.y, uv.x) + r*20. + t;

    return smoothstep(sz.y, sz.x, r*(1.+2.*posSin(a*5.)));
}

float starField(vec2 uv, float t){
    uv.y = abs(uv.y);

    uv += waterDistort(uv, t);

    vec2 st = uv * 10.;

    vec2 f = fract(st) - 0.5;

    float rv = 0.;

    for(float i=-1;i<=1;i++){
        for(float j=-1.; j<=1.; j++){
            vec2 st2 = st + vec2(i,j);

            vec2 id = floor(st2);

            float n1 = noise(id) * 0.9 + 0.1;
            float n2 = noise(id.yx) * 0.9 + 0.1;

            float sz = n1/10.;
            rv += star(f - vec2(i, j), vec2(n1*2.-1.,n2*2.-1.), vec2(sz/2., sz), n1 + 0.3*sin(t+n2));
        }
    }

    return rv;
}

float moon(vec2 uv, float t){
    vec2 pos = vec2(-0.6, 0.25);

    uv.y = abs(uv.y);

    return smoothstep(0.1, 0.09, length(uv - pos));
}

vec3 getUVColor(vec2 uv, float t){
    vec3 col = vec3(0.);

    float scrAspect = u_resolution.y/u_resolution.x;

    vec3 black = vec3(0.);
    vec3 brown = vec3(0.7,0.4,0.1) * 0.2;
    vec3 white = vec3(1.);
    vec3 green = vec3(0.3,0.7,0.1);
    vec3 blue = vec3(0., 0., 0.1);

    //sky
    col += mix(black, blue, getVignette(uv/2., 0.9, scrAspect));

    //star
    col += starField(uv, t);

    col += moon(uv, t);

    return col;
}

void main() {
    vec2 uv = gl_FragCoord.xy / u_resolution;
    uv -= 0.5;
    uv.x *= u_resolution.x / u_resolution.y;

    vec3 color = vec3(0.);
    float t = u_time;

    uv += vec2(0., 0.15);

    color += getUVColor(uv - getLayerDistortion(uv, t), t);

//    color = vec3(getLayerDistortion(uv, t), 0.);
//    color.rg = waterDistort(uv, t);
//    color.b = 0.;

    gl_FragColor = vec4(color, 1.);
}

