#version 150

uniform sampler2D DiffuseSampler;

uniform int passes;
uniform vec2 center;
uniform float intensity;
uniform float radius;

in vec2 texCoord;

out vec4 fragColor;

void main() {

    fragColor = vec4(0.);

    vec2 offset = texCoord - center;

    for(int i=0; i<passes; i++) {
        float scale = 1. - intensity * (float(i) / float(passes)) * (clamp(length(offset) / radius, 0., 1.));
        fragColor.rgb += texture(DiffuseSampler, offset * scale + center).rgb;
    }
    fragColor /= passes;
    fragColor *= 2.;
    fragColor -= 1.;
    fragColor = -fragColor*fragColor*fragColor*fragColor+1.;

    fragColor.a = 1.;
}
