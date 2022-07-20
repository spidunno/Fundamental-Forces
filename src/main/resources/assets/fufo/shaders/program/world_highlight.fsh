#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;
uniform sampler2D NormalMap;

uniform samplerBuffer Data;
uniform int instanceCount;

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform vec3 cameraPos;

in vec2 texCoord;

out vec4 fragColor;

#moj_import <fufo:common_math>

float fetch(int index) {
    return texelFetch(Data, index).r;
}

void main() {
    vec3 orgCol = texture(DiffuseSampler, texCoord).rgb;
    fragColor = vec4(orgCol, 1.);

    vec3 worldPos = getWorldPos(texture(MainDepthSampler, texCoord).r, texCoord, invProjMat, invViewMat, cameraPos);
    vec3 normalVec = texture(NormalMap, texCoord).rgb * 2. - 1.;

    for (int ins=0; ins<instanceCount; ins++) {
        int i = ins*7;

        vec3 center = vec3(fetch(i), fetch(i+1), fetch(i+2));
        float radius = fetch(i+3);

        float distToCenter = distance(worldPos, center);
        if (distToCenter < radius) {
            vec3 color = vec3(fetch(i+4), fetch(i+5), fetch(i+6));

            vec3 lightVec = normalize(center - worldPos);
            float lightFactor = max(dot(lightVec, normalVec), 0.);
            vec3 addCol = color * lightFactor * (radius-distToCenter)/radius;
//            fragColor.xyz += addCol;
//            fragColor.xyz += min(addCol, addCol * (orgCol * 8.) + (addCol / 8.));
            fragColor.xyz *= (addCol+1.);
        }
    }
}