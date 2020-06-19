#ifdef GL_ES
precision highp float;
#endif

#define PI 3.14159265359

uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

uniform vec2 u_resolution;
uniform float u_time;

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 vPosition;

//--------------------------------------------------------------------------------------------------
struct Ray{
    vec3 o, d;
};

Ray getRay(vec2 uv, vec3 camPos, vec3 lookAt, float zoom){
    Ray a;
    a.o = camPos;

    vec3 forward = normalize(lookAt - camPos);
    vec3 right = cross(vec3(0,1,0), forward);
    vec3 up = cross(forward, right);
    vec3 centerScreen = a.o + forward * zoom;
    vec3 screenIntersection = centerScreen + uv.x * right + uv.y*up;

    a.d = normalize(screenIntersection - a.o);

    return a;
}

vec3 closestPoint(Ray r, vec3 p){
    return r.o + max(0.,dot(p-r.o,r.d))*r.d;
}

float distRay(Ray r, vec3 p){
    return length(p - closestPoint(r,p));
}

float bokeh(Ray r, vec3 p, float size, float blur){
    float d = distRay(r, p);
    //to compensate for size reduction as things go far away
    size *= length(p);

    float c = smoothstep(size, size*(1. - blur), d);
    c *= mix(0.6, 1.0,smoothstep(0.8*size, size, d));

    return c;
}

float noise(float t){
    return fract(sin(t*3456.)*6547.);
}

vec4 noise14(float t){
    return fract(sin(t*vec4(123., 1024., 3456., 9564.))*vec4(6547., 345., 8799., 1564.));
}

vec3 streetLights(Ray r, float t){
    //fold space horzintally
    float side = step(r.d.x, 0.);
    r.d.x = abs(r.d.x);

    float numSteps = 1./5.;
    float mask = 0.;
    for(float i = 0.; i<1.; i+=numSteps){
        float ti = fract(t+i+side*numSteps*0.5);
        vec3 p = vec3(2., 2., 100. - ti*100.);
        mask += bokeh(r, p, 0.05, 0.1) * ti *ti *ti;
    }

    return vec3(1, .7, .3) * mask;
}

vec3 envLights(Ray r, float t){
    //fold space horzintally
    float side = step(r.d.x, 0.);
    r.d.x = abs(r.d.x);

    vec3 color = vec3(0.);

    float numSteps = 1./10.;
    float mask = 0.;
    for(float i = 0.; i<1.; i+=numSteps){
        float ti = fract(t+i+side*numSteps*0.5);
        vec4 n = noise14(i + side*100.);

        float fade = ti*ti*ti;
        float occlusion = sin(ti*2.*PI*10.*n.x)  * 0.5 + 0.5;
        fade = occlusion;

        float x = mix(2.5, 10., n.x);
        float y = mix(.1,1.5, n.y);

        vec3 p = vec3(x, y, 50. - ti*50.);

        vec3 col = n.zyx;
        color += bokeh(r, p, 0.05, 0.1) * fade * col*0.5;
    }

    return color;
}

vec3 headLights(Ray r, float t){
    t*= 2.;

    float w1 = 0.25;
    float w2 = w1*1.2;

    float numSteps = 1./30.;
    float mask = 0.;
    for(float i = 0.; i<1.; i+=numSteps){
        float n = noise(i);
        if(n>0.1){
            continue;
        }

        float ti = fract(t+i);
        float z = 100. - ti*100.;
        float fade = ti*ti*ti*ti*ti;
        float focus = smoothstep(0.9, 1., ti);

        float size = mix(.05,.03,focus);

        mask += bokeh(r, vec3(-1. - w1,0.15,z), size, 0.1)*fade;
        mask += bokeh(r, vec3(-1. + w1,0.15,z), size, 0.1)*fade;

        mask += bokeh(r, vec3(-1. - w2,0.15,z), size, 0.1)*fade;
        mask += bokeh(r, vec3(-1. + w2,0.15,z), size, 0.1)*fade;

        float ref = 0.;
        ref += bokeh(r, vec3(-1. - w2, -0.15,z), size*3., 1.)*fade;
        ref += bokeh(r, vec3(-1. + w2, -0.15,z), size*3., 1.)*fade;

        mask += ref * focus;
    }

    return vec3(.9,.9,1.) * mask;
}

vec3 tailLights(Ray r, float t){
    t *= 0.25;
    float w1 = 0.25;
    float w2 = w1*1.2;

    float numSteps = 1./15.;
    float mask = 0.;
    for(float i = 0.; i<1.; i+=numSteps){
        float n = noise(i);
        if(n>0.5){
            continue;
        }

        float lane = step(0.25, n);

        float ti = fract(t+i);
        float z = 100. - ti*100.;
        float fade = ti*ti*ti*ti*ti;
        float focus = smoothstep(0.9, 1., ti);

        float size = mix(.05,.03,focus);

        float laneShift = smoothstep(1.,.96,ti);
        float x = 1.5 - lane * laneShift;

        float blink = step(0.,sin(t*1000.)) * 7. * lane * step(0.96, ti);

        mask += bokeh(r, vec3(x - w1,0.15,z), size, 0.1)*fade;
        mask += bokeh(r, vec3(x + w1,0.15,z), size, 0.1)*fade;

        mask += bokeh(r, vec3(x - w2,0.15,z), size, 0.1)*fade;
        mask += bokeh(r, vec3(x + w2,0.15,z), size, 0.1)*fade*(1.+blink);

        float ref = 0.;
        ref += bokeh(r, vec3(x - w2, -0.15,z), size*3., 1.)*fade;
        ref += bokeh(r, vec3(x + w2, -0.15,z), size*3., 1.)*fade*(1.+blink*0.1);

        mask += ref * focus;
    }

    return vec3(1.,.1,.03 ) * mask;
}

vec2 rain(vec2 uv, float t, float speed){
    t *= speed;

    vec2 a = vec2(2.,1.);
    vec2 st = uv*a;

    vec2 id = floor(st);

    float oldIDY = id.y;

    st.y += t*0.3;
    float n = fract(sin(id.x*716.34)*768.34);
    st.y += n;
    uv.y += n;


    id = floor(st);
    st = fract(st) - 0.5;
//    st.x += sin(st.y);

    t += fract(sin(id.x*76.34 + id.y*1453.7)*768.34)*2.*PI;
    float sinAng = t+sin(t+sin(t)*.5);
    float y = -sin(sinAng) * 0.43;
    float x = sin(uv.y*((noise(id.x))*PI/8. + PI/8.) + noise(id.x)*2.*PI)*0.35;
    vec2 p1 = vec2(x, y);
    vec2 offset1 = (st-p1)/a;
    float d = length(offset1);
    float m1 = smoothstep(0.06,0.0, d);

    vec2 offset2 = (fract(uv*a.x*vec2(1.,2.))-0.5)/vec2(1.,2.) - vec2(p1.x,0.);
    d = length(offset2);
    float m2 = smoothstep(0.2*(0.5 - st.y),0., d) * smoothstep(0., .1, st.y - p1.y);

    if(st.x > .46 || st.y > 0.49){
//        m1 = 1.;
    }
//    return vec2(m1+m2);
    return vec2(m1*offset1*30.+m2*offset2*10.);
}

void main() {
    vec3 color = vec3(0.);

    vec2 uv = gl_FragCoord.xy / u_resolution;
    uv -= 0.5;
    uv.x *= u_resolution.x / u_resolution.y;

    vec3 camPos = vec3(0.5, .2, 0);
    vec3 lookAt = vec3(0.4, .2, 1.);

    float t = u_time*0.05;
    vec2 rainDistort = rain(uv*5., t, 100.) * 0.5;
    rainDistort += rain(uv*11., t, 50.) * .5;
    rainDistort += rain(uv*23., t, 20.) * .5;

    uv.x += sin(uv.y*70.)*0.0025;
    uv.y += sin(uv.x*170.)*0.0015;

    Ray r = getRay(uv-rainDistort*0.5 , camPos, lookAt, 2.);

    color += streetLights(r, t);
    color += headLights(r, t);
    color += tailLights(r, t);
    color += envLights(r, t);

    color += (r.d.y + 0.25)*vec3(.2, .1, .5);

//    color = vec3(rainDistort, 0.);
    gl_FragColor = vec4(color, 1.);
}

