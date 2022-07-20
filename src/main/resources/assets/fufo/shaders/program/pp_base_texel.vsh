#version 150

uniform vec2 InSize;

in vec4 Position;

out vec2 texCoord;
out vec2 oneTexel;

void main(){
    gl_Position = vec4(Position.xy - 1.0, 0.0, 1.0);

    texCoord = Position.xy * 0.5;
    oneTexel = 1.0 / InSize;
}
