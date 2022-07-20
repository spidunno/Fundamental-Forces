#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MixSampler;

uniform float m;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    fragColor = vec4(mix(
        texture(DiffuseSampler, texCoord).rgb,
        texture(MixSampler, texCoord).rgb,
        m
    ), 1.);
}