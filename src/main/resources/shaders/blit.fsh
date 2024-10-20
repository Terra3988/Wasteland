#version 460 core

out vec4 FragColor;

in vec2 fTexCoords;

uniform sampler2D uTextureUnit;

void main()
{
    FragColor = texture(uTextureUnit, fTexCoords);
}
