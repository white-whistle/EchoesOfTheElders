#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>
#moj_import <projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

out vec4 texProj0;
out vec2 texCoord0;
out vec4 vertexColor;


void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    texProj0 = projection_from_position(gl_Position);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);

    texCoord0 = UV0;
}
