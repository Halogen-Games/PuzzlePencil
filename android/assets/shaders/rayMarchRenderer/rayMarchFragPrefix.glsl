#define PI 3.14159265359

#define MAX_STEPS 100
#define MIN_DISTANCE 0.01
#define MAX_DISTANCE 100.

uniform sampler2D u_texture;//default GL_TEXTURE0, expected by SpriteBatch

uniform vec2 u_resolution;
uniform float u_fboHeight;
uniform float u_time;

varying vec4 vColor;
varying vec2 v_texCoords;
varying vec4 vPosition;

struct Camera{
    vec3 position;
    vec3 lookAt;
    float fov;
    float zoom;
};

struct Light{
    vec3 position;
};

struct Box{
    vec3 center;
    vec3 size;
};

struct Sphere{
    vec3 center;
    float radius;
};

struct Capsule{
    vec3 a;
    vec3 b;
    float radius;
};

struct Cylinder{
    vec3 a;
    vec3 b;
    float radius;
};

struct Torus{
    vec3 center;
    float radPlanar;
    float radCross;
};

/*
returns distance of point p from the sphere defined by cen and rad
*/
float distSphere(vec3 p, vec3 cen, float rad){
    p -= cen;
    return length(p) - rad;
}

/*
returns distance of point p from the capsule defined by a, b and radius
*/
float distCapsule(vec3 p, Capsule c){
    vec3 ab = c.b - c.a;
    vec3 ap = p - c.a;

    float t = dot(ab,ap)/dot(ab, ab);

    t = clamp(t, 0., 1.);
    vec3 x = c.a + t*ab;
    return length(p - x) - c.radius;
}

/*
returns distance of point p from the cylinder defined by a, b and radius
*/
float distCylinder(vec3 p, Cylinder c){
    vec3 ab = c.b - c.a;
    vec3 ap = p - c.a;

    float t = dot(ab,ap)/dot(ab, ab);
    vec3 pCore = c.a + t*ab;

    float d = length(p - pCore) - c.radius;

    float y = (abs(t - 0.5) - 0.5) * length(ab);

    float exteriorDist = length(max(vec2(d, y),0.));
    float interiorDist = min(max(d, y), 0.);

    return interiorDist + exteriorDist;
}

/*
returns the distamnce of point p from the XZ aligned torus with center at cen, and radius as rPlane and rCross
*/
float distTorusXZ(vec3 p, Torus t){
    p -= t.center;
    float py = p.y;
    float pr = length(p.xz) - t.radPlanar;

    float distCore = length(vec2(py, pr));

    return distCore - t.radCross;
}

/*
returns the distamnce of point p from axis aligned box
*/
float distAlignedBox(vec3 p, Box b){
    p -= b.center;
    p = abs(p)-b.size;
    return length(max(p, 0.))+min(max(p.x, max(p.y, p.z)), 0.);
}

/*
returns distance of point p from the plane XZ
*/
float distXZ(vec3 p){
    return p.y;
}

/*
returns distance of point p from the scene
*/
float distScene(vec3 p);

/*
returns normal at point p (p should be on an object in scene)
*/
vec3 getNormal(vec3 p){
    vec2 offset = vec2(0.01, 0);
    float d = distScene(p);
    vec3 n = d - vec3(
    distScene(p - offset.xyy),
    distScene(p - offset.yxy),
    distScene(p - offset.yyx)
    );
    return normalize(n);
}

/*
raymarches to return distance of ro from scene in rd direction
*/
float rayMarch(vec3 ro, vec3 rd){
    float d0;
    for(int i=0; i<MAX_STEPS; i++){
        vec3 p = ro + d0 * rd;
        float distance = distScene(p);
        d0 += distance;
        if(distance<MIN_DISTANCE || d0>MAX_DISTANCE){
            break;
        }
    }
    return d0;
}

/*
returns point in the scene projected on given screen coordinate with given camera
*/
vec3 getPointInScene(vec2 coord, Camera c){
    vec3 lookDir = normalize(c.lookAt - c.position);
    vec3 rd = normalize(vec3(coord.x, coord.y, c.zoom));

    float d = rayMarch(c.position, rd);
    vec3 p = c.position + d*rd;

    if(d >= MAX_DISTANCE){
        return vec3(MAX_DISTANCE + 1.0, 0., 0.);
    }

    return p;
}

/*
returns lighting at point p in scene
*/
float getLight(vec3 p){
    Light light;
    light.position = vec3(0.,5.,0.);
    light.position.xz += vec2(sin(u_time)*3., cos(u_time)*3.);

    vec3 l = normalize(light.position - p);
    vec3 n = getNormal(p);

    float diff = clamp(dot(l,n) ,0. ,1.);

    float d = rayMarch(p + MIN_DISTANCE*n*2., l);
    if(d < length(light.position - p)){
        diff *= 0.5;
    }

    float ambient = 0.1f;

    float intensity = diff * ( 1. - ambient) + ambient;

    return intensity;
}

void main() {
    vec3 color = vec3(0.);
    vec2 coord = (gl_FragCoord.xy - u_resolution/2.) / u_fboHeight;

    Camera cam;
    cam.position = vec3(0.,1.,-10.);
    cam.lookAt = vec3(0.,0.,0.);
    cam.fov = 120.;
    cam.zoom = 1.;

    vec3 p = getPointInScene(coord, cam);
    float diffuse = getLight(p);

    color = vec3(0);
    color += diffuse;

    gl_FragColor = vec4(color, 1.);
}
