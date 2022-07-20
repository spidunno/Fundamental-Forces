#version 150

uniform sampler2D DiffuseSampler;

uniform vec2 InSize;
uniform vec2 range;
uniform float threshold;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void main() {
    ivec2 texelRadius = ivec2(range * InSize.y);

    for (int x=-texelRadius.x;x<=texelRadius.x;x++) {
        if (texture(DiffuseSampler, texCoord + oneTexel*vec2(x,0.)).r > threshold) {
            fragColor = vec4(1.);
            return;
        }
    }
    for (int y=-texelRadius.y;y<=texelRadius.y;y++) {
        if (texture(DiffuseSampler, texCoord + oneTexel*vec2(0.,y)).r > threshold) {
            fragColor = vec4(1.);
            return;
        }
    }
    fragColor = vec4(0.,0.,0.,1.);
}