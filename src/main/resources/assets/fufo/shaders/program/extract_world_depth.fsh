#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform float fov;
uniform float aspectRatio;
uniform float nearPlaneDistance;
uniform float farPlaneDistance;

in vec2 texCoord;

out vec4 fragColor;

#moj_import <fufo:common_math>

void main() {
    float depth = texture(MainDepthSampler, texCoord).r;
    float worldDepth = getWorldDepth(depth, nearPlaneDistance, farPlaneDistance, texCoord, fov, aspectRatio);

    fragColor = vec4(vec3(worldDepth / (farPlaneDistance * .25)), 1.);
}