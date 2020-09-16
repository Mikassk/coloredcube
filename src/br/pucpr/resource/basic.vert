#version 330

in vec3 aPosition;
in vec3 aColor;

out vec3 vColor;

uniform mat4 uWorld;

void main(){
    gl_Position = uWorld * vec4(aPosition, 1.0);
    vColor = aColor;
}