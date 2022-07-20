#version 150

uniform sampler2D DiffuseSampler;

uniform float passes;
uniform float radius;
uniform float lossiness;
uniform float preserveOriginal;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

#define PI2 6.2831853072 // PI * 2
#define PI_O_2 1.5707963268 // PI / 2

//https://www.shadertoy.com/view/3dSBDD
void main() {
    float count = 1.0 + preserveOriginal;
    vec4 color = texture(DiffuseSampler, texCoord) * count;
    float directionStep = PI2 / passes;

    vec2 off;
    float c, s, dist, dist2, weight;
    for(float d = 0.0; d < PI2; d += directionStep) {
        c = cos(d);
        s = sin(d);
        dist = 1.0 / max(abs(c), abs(s));
        dist2 = dist * (2.0 + lossiness);
        off = vec2(c, s);
        for(float i= dist * 1.5; i <= radius; i += dist2) {
            weight = i / radius;
            count += weight;
            color += texture(DiffuseSampler, texCoord + off * oneTexel * i) * weight;
        }
    }

    fragColor =  color / count;
}