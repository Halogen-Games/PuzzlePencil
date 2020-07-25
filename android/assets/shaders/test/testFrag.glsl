#ifdef GL_ES
precision highp float;
#endif

#define PI 3.14159265359
#define PI2 6.28318530718

#define red vec3(1., 0., 0.)
#define green vec3(0., 1., 0.)

uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

uniform vec2 u_resolution;
uniform float u_time;

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 vPosition;

/*-----------------------------------------Common Funcs-------------------------------------------*/
float plotY(float x, vec2 uv){
    return smoothstep(x - 0.005, x, uv.x) * smoothstep(x + 0.005, x, uv.x);
}

float plotX(float y, vec2 uv){
    return smoothstep(y - 0.005, y, uv.y) * smoothstep(y + 0.005, y, uv.y);
}

vec3 getColorSpike(vec3 c1, vec3 c2, float st, float en, float val){
    float strength = step(st, val);
    strength *= step(-en, -val);

    return strength*mix(c1, c2, smoothstep(0., 1., (val - st) / (en - st)));
}

vec3 drawAxis(vec2 uv, vec3 color){
    color.rgb = mix(color.rgb, red, plotX(0., uv));

    color.rgb = mix(color.rgb, green, plotY(0., uv));

    return color;
}
/*------------------------------------------------------------------------------------------------*/
void main() {
    vec2 uv = (gl_FragCoord.xy - .5*u_resolution.xy) / u_resolution.y;
    vec4 color = vec4(vec3(0.),1.);

    float t = u_time;
    vec2 st  = uv;

    /*distortions*/
    vec2 dist = vec2(0.);

    float angle = atan(uv.y, uv.x) * 180. / PI;
    float r = length(uv);

    float angDiff = sin(r*10.*PI2 + t*10.)*60.;
//    angle += angDiff;

    uv = r * vec2(cos(angle*PI/180.), sin(angle*PI/180.));

//    uv += dist;

    float thickness = 0.01 * (1. + abs(angDiff)/60.);
    color += smoothstep(-thickness, 0.0, uv.y) * smoothstep(thickness, 0., uv.y) * smoothstep(-0.01, 0.01, uv.x);

    gl_FragColor = color;
}

