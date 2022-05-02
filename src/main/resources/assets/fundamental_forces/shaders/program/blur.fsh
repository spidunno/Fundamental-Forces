#version 150

uniform sampler2D Sampler0; // texture to blur
uniform vec2 InSize;
uniform int KernelSize; // the higher the wider the blur

in vec2 texCoord;

out vec4 fragColor;

void main()
{
    vec2 uv = texCoord;
    vec2 dUV = 1.0 / InSize;

    vec4 total;

    for (int i = -KernelSize; i <= KernelSize; i++)
        total += texture(Sampler0, uv + vec2(dUV.x * i, 0.0));

    total /= KernelSize * 2;

    fragColor = total;
}