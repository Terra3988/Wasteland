#version 460 core

layout(location = 0) in vec3 aPosition;
layout(location = 1) in vec2 aTexCoords;

out vec2 fTexCoords;

uniform mat4 uProjview;

void main()
{
    fTexCoords = aTexCoords;
    gl_Position = uProjview * vec4(aPosition, 1.0f);
}