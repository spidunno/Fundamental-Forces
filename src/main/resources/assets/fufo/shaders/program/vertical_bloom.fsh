#version 330

uniform vec2 ScreenSize;

uniform sampler2D texture0;
uniform sampler2D texture1;
uniform sampler2D texture2;

uniform float Brightness; // the higher the brighter the glow
uniform int KernelSize; // the higher the taller the blur (and thus glow)
uniform float BlurDeviation; // standard deviation of blur. higher, blurrier

out vec4 outColor;

float gaussian(float x)
{
	float s = 2 * BlurDeviation * BlurDeviation;
	return 1.0 / sqrt(s * 3.14) * exp(-(x * x / s));
}

void main()
{
	vec2 uv = gl_FragCoord.xy/ScreenSize;
	vec2 dUV = 1.0 / ScreenSize;

	vec4 total;
	
	for (int i = -KernelSize; i <= KernelSize; i++)
		total += texture(texture0, uv + vec2(0.0, dUV.y * i)) * gaussian(i);
		
	if (texture(texture1, uv) == vec4(1))
		outColor = texture(texture2, uv);
	else
		outColor = total * Brightness;
}