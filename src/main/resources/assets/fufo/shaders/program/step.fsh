#version 150

uniform sampler2D DiffuseSampler;

uniform float threshold;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    float color = texture(DiffuseSampler, texCoord).r;
    color = step(threshold, color);
    fragColor = vec4(color, color, color, 1.);
}