#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform vec3 cameraPos;

uniform vec3 center;
uniform float radius;
uniform float width;

in vec2 texCoord;

out vec4 fragColor;

#moj_import <fufo:common_math>

void main() {
    vec3 worldPos = getWorldPos(texture(MainDepthSampler, texCoord).r, texCoord, invProjMat, invViewMat, cameraPos);
    float dist = distance(worldPos, center);
    float ringDist = abs(dist - radius);
    fragColor = texture(DiffuseSampler, texCoord);
    if (ringDist < width) {
        fragColor.xyz += vec3(width-ringDist);
    }
}