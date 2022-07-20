#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

float diffFinalProcess(float diff) {
    diff *= 3;
    return diff*diff*diff*diff;
}

void main(){
    const float offsetMul = 1.;
    float center = texture(DiffuseSampler, texCoord).r;
    float left   = texture(DiffuseSampler, texCoord - vec2(oneTexel.x*offsetMul, 0.0)).r;
    float right  = texture(DiffuseSampler, texCoord + vec2(oneTexel.x*offsetMul, 0.0)).r;
    float up     = texture(DiffuseSampler, texCoord - vec2(0.0, oneTexel.y*offsetMul)).r;
    float down   = texture(DiffuseSampler, texCoord + vec2(0.0, oneTexel.y*offsetMul)).r;
    float leftDiff  = diffFinalProcess(center - left);
    float rightDiff = diffFinalProcess(center - right);
    float upDiff    = diffFinalProcess(center - up);
    float downDiff  = diffFinalProcess(center - down);
    float total = clamp(leftDiff + rightDiff + upDiff + downDiff, 0.0, 1.0);
    fragColor = vec4(total, total, total, 1.0);
}
