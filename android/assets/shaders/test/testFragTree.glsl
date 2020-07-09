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
float taperBox(vec2 p, float wb, float wt, float yb, float yt, float blur){
    float width = mix(wb, wt,(p.y - yb)/(yt - yb));

    float m = smoothstep(-blur, blur, p.y-yb);
    m *= smoothstep(blur, -blur, p.y-yt);
    m *= smoothstep(blur, -blur, abs(p.x) - width);

    return m;
}

vec4 tree(vec2 uv, vec3 color, float blur){
    //trunk
    float m = taperBox(uv, .03, .03, .0, .25, blur);

    //lower to higher parts
    m += taperBox(uv, .2, .1, .25, .5, blur);
    m += taperBox(uv, .15, .05, .5, .75, blur);
    m += taperBox(uv, .1, .0, .75, 1. , blur);

    //shadow
    float shadow = taperBox(uv - vec2(.2, 0.), .1, .5, .15, .25, blur);
    shadow += taperBox(uv + vec2(.25, 0.), .1, .5, .15, .25, blur);

    color -= shadow;

    return vec4(color, m);
}

void main() {
    vec2 uv = (gl_FragCoord.xy - .5*u_resolution.xy) / u_resolution.y;
    uv.y += 0.5;
    vec4 color = vec4(0.);
    float t = u_time;

    float blur = 0.01;

    vec4 tree = tree(uv, vec3(1.) , blur);

    color = mix(color, tree, tree.a);

//    float thickness = 1./u_resolution.x;
//    if(abs(uv.x) < thickness){
//        color.g = 1.;
//    }
//    if(abs(uv.y) < thickness){
//        color.r = 1.;
//    }

    gl_FragColor = color;
}

