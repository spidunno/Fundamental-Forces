#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform vec3 cameraPos;
uniform mat4 invViewMat;
uniform mat4 invProjMat;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

#moj_import <fufo:common_math>

vec3 calcWorldPos(vec2 tc) {
    return getWorldPos(texture(MainDepthSampler, tc).r, tc, invProjMat, invViewMat, cameraPos);
}

void main(){
    vec2 offset = oneTexel;

    vec2 center = texCoord;
    vec2 left = texCoord - vec2(offset.x, 0.0);
    vec2 right = texCoord + vec2(offset.x, 0.0);
    vec2 up = texCoord - vec2(0.0, offset.y);
    vec2 down = texCoord + vec2(0.0, offset.y);

    vec3 centerWP = calcWorldPos(center);
    vec3 leftWP = calcWorldPos(left);
    vec3 rightWP = calcWorldPos(right);
    vec3 upWP = calcWorldPos(up);
    vec3 downWP = calcWorldPos(down);

    vec3 c2left = leftWP - centerWP;
    vec3 c2right = rightWP - centerWP;
    vec3 c2up = upWP - centerWP;
    vec3 c2down = downWP - centerWP;

    vec3 normalA = normalize(cross(c2left, c2down));
    vec3 normalB = normalize(cross(c2up, c2left));
    vec3 normalC = normalize(cross(c2right, c2up));
    vec3 normalD = normalize(cross(c2down, c2right));

    fragColor = vec4((-normalize(normalA+normalB+normalC+normalD)+1.)/2., texture(DiffuseSampler, vec2(0.)).a+1.);
}
