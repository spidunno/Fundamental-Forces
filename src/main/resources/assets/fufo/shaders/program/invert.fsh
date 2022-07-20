#version 150

uniform sampler2D DiffuseSampler;

uniform int invert;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    if (invert == 1) {
        fragColor = vec4(1. - texture(DiffuseSampler, texCoord).rgb, 1.);
    } else {
        fragColor = vec4(texture(DiffuseSampler, texCoord).rgb, 1.);
    }
}