#version 330

uniform sampler2D texture0;

uniform vec2 ScreenSize;

out vec4 outColor;

float gaussian(float x)
{
	float s = 2 * 3.5 * 3.5;
	return 1.0 / sqrt(s * radians(180)) * exp(-x * x / s);
}

void main()
{
    vec2 uv = gl_FragCoord.xy / ScreenSize;
    vec2 dUV = 1.0 / ScreenSize;
    vec4 col = texture(texture0, uv) * gaussian(0);
    
    for (int i = 1; i < 32; i++)
    {
    	vec2 offset = vec2(dUV.x * i, 0.0);
    	float weight = gaussian(i);
    	col += texture(texture0, uv + offset) * weight;
    	col += texture(texture0, uv - offset) * weight;
    }
    
    outColor = col;
}