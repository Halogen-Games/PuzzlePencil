float distScene(vec3 p){
    float d = distXZ(p);

    Sphere s;
    s.center = vec3(3., 0.3, 5.);
    s.radius = 0.3;
    d = min(d, distSphere(p, s));

    Capsule c;
    c.a = vec3(-1., 0.3, 6.);
    c.b = vec3(1.8, 0.3, 2.5);
    c.radius = 0.3;
    d = min(d, distCapsule(p, c));

    Cylinder cyl;
    cyl.a = vec3(-1., 0.1, 0.5);
    cyl.b = vec3(0., 0.1, 2.5);
    cyl.radius = 0.1;
    d = min(d, distCylinder(p, cyl));

    Torus t;
    t.radPlanar = 3.;
    t.radCross = 0.2;
    t.center = vec3(7.,0.2,7.);
    d = min(d, distTorusXZ(p, t));

    Box b;
    b.size = vec3(1.,1.,1.);
    b.center = vec3(-5., 1., 5.);
    d = min(d, distAlignedBox(p, b));

    s.center = vec3(0., 0.2, 0.);
    s.radius = 0.2;
    float ds = distSphere(p, s);

    b.center = vec3(0., 1., 0.);
    b.size = vec3(0.5, 5., 0.5);
    float db = distAlignedBox(p, b);

    d = min(d, mix(ds, db, 0.5));

    return d;
}
