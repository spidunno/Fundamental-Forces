#version 150

uniform sampler2D DiffuseSampler;

uniform float time;
uniform vec2 center;
uniform vec2 noiseScale;
uniform vec2 flow;

in vec2 texCoord;

out vec4 fragColor;

// https://www.shadertoy.com/view/XdXGW8
vec2 grad(ivec2 z) {
    // 2D to 1D  (feel free to replace by some other)
    int n = z.x+z.y*11111;

    // Hugo Elias hash (feel free to replace by another one)
    n = (n<<13)^n;
    n = (n*(n*n*15731+789221)+1376312589)>>16;

    // simple random vectors
    return vec2(cos(float(n)),sin(float(n)));
}

float noise(vec2 p) {
    ivec2 i = ivec2(floor( p ));
    vec2 f = fract( p );

    vec2 u = f*f*(3.0-2.0*f); // feel free to replace by a quintic smoothstep instead

    return mix( mix( dot( grad( i+ivec2(0,0) ), f-vec2(0.0,0.0) ),
    dot( grad( i+ivec2(1,0) ), f-vec2(1.0,0.0) ), u.x),
    mix( dot( grad( i+ivec2(0,1) ), f-vec2(0.0,1.0) ),
    dot( grad( i+ivec2(1,1) ), f-vec2(1.0,1.0) ), u.x), u.y);
}

void main() {
    vec2 offset = texCoord - center;
    float theta = atan(offset.y / offset.x);
    if (offset.x > 0.) theta += 3.1415;
    float r = length(offset);

    vec3 orgCol = texture(DiffuseSampler, texCoord).rgb;
//    orgCol = vec3(1.);
    orgCol *= noise(vec2(theta, r) * noiseScale + flow * time);
    fragColor = vec4(orgCol, 1.);
}