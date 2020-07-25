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

    float t = u_time*2.;
    vec2 st  = uv;

    /*distortions*/
    vec2 dist = vec2(0.);

    //sin
    st *= 1.;
//    dist += sin((st.xy + t*0.2)*PI2) * 0.2;
//    dist += sin((st.yx + t*0.2)*PI2) * 1.;
//    dist += sin((cos(st.yx) + t*0.2)*PI2) * 1.;
    dist += sin((st.x + t*0.2)*PI2) * cos((st.y*3. + t*0.2)*PI2);

    /*patterns*/
    st = uv * 10.;
    t = u_time;

    st += dist;
    vec2 id = floor(st);
    vec2 f = fract(st);

    //bars
    //color += step(0.5, fract(uv.x));
    //color += smoothstep(0.22, 0.28, f.x) * smoothstep(0.78, 0.72, f.x);

    //check
    //color += step(0.5, fract(uv.x + floor(uv.y)*0.5));
    //color += step(0.5, fract(st.x + floor(st.y*2.)*0.5));
    //float fDash = fract(st.x + floor(st.y*2.)*0.5);
//    float blur = 0.01;
//    color.rgb += abs(smoothstep(0.25-blur, 0.25+blur, f.x) * smoothstep(0.75+blur, 0.75-blur, f.x) - smoothstep(0.25-blur, 0.25+blur, f.y) * smoothstep(0.75+blur, 0.75-blur, f.y)) * 0.5 + 0.5;

    //stones, kinda
    st.y += t*(mod(id, 2)*2. - 1.);
    f = fract(st) - 0.5;
    color.rgb += smoothstep(0.4, 0.35, length(f));



    //grid
//    color.rgb = drawAxis(uv, color.rgb);

    //dist mask
//    color.rgb = vec3(dist*.5+.5, 0.);

    gl_FragColor = color;
}

