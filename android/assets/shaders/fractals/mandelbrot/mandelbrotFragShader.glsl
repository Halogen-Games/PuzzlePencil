precision highp float;

#define PI 3.14159265359

#define MAX_STEPS 100.
#define MIN_DISTANCE 0.01
#define MAX_DISTANCE 100.

uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

uniform vec2 u_resolution;
uniform float u_time;

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 vPosition;

vec2 coord;

vec2 multComplex(vec2 a, vec2 b){
    return vec2(a.x*b.x - a.y*b.y, a.x*b.y + a.y*b.x);
}

void main() {
    vec3 color = vec3(0.);
    coord = (gl_FragCoord.xy - u_resolution/2.) / u_resolution.y;
    color = vec3(0);

    float steps = 0.;

    vec2 z = vec2(0.);

    vec2 c = coord * 2. / pow(2., u_time) + vec2(-1.5,0.);

    for(; steps<MAX_STEPS; steps++){
        z = multComplex(z,z) + c;
        if(length(z) > 2.){
            break;
        }
    }

    float s = steps / MAX_STEPS;

    color += s;

    gl_FragColor = vec4(color, 1.);
}

