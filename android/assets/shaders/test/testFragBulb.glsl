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
float getLight(vec2 uv){
    return smoothstep(0.8, 0.0, length(uv));
}

float getParticles(vec2 uv, float t){
    vec2 st = fract(uv * 20) - .5;
    return smoothstep(0.1,0.09, length(st*vec2(5,1)));
}

float getShadow(vec2 uv){
    float angle = atan2(uv.y, uv.x)/PI2 + 0.5;
    float steps = 5;
    float shadow = step(.5,fract(angle * steps));

    return shadow;
}

void main() {
    vec2 uv = (gl_FragCoord.xy - .5*u_resolution.xy) / u_resolution.y;
    vec4 color = vec4(0.);
    float t = u_time;

    //light
    vec4 lightColor = vec4(1.,1.,0.4,1.);
    color += getLight(uv) * lightColor;

    //particles
    vec4 particleColor = vec4(1.,1.,0.7,1.);
    color += getParticles(uv, t) * particleColor;

    //shadows
    color *= getShadow(uv);

    //cap
    color *= smoothstep(0.199, 0.2, length(uv));

    gl_FragColor = color;
}

