#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform float GameTime;
uniform int EndPortalLayers;

in vec4 texProj0;
in vec2 texCoord0;

const vec3[] COLORS = vec3[](
vec3(0.022087, 0.098399, 0.110818),
vec3(0.011892, 0.095924, 0.089485),
vec3(0.027636, 0.101689, 0.100326),
vec3(0.046564, 0.109883, 0.114838),
vec3(0.064901, 0.117696, 0.097189),
vec3(0.063761, 0.086895, 0.123646),
vec3(0.084817, 0.111994, 0.166380),
vec3(0.097489, 0.154120, 0.091064),
vec3(0.106152, 0.131144, 0.195191),
vec3(0.097721, 0.110188, 0.187229),
vec3(0.133516, 0.138278, 0.148582),
vec3(0.070006, 0.243332, 0.235792),
vec3(0.196766, 0.142899, 0.214696),
vec3(0.047281, 0.315338, 0.321970),
vec3(0.204675, 0.390010, 0.302066),
vec3(0.080955, 0.314821, 0.661491)
);

const mat4 SCALE_TRANSLATE = mat4(
0.5, 0.0, 0.0, 0.25,
0.0, 0.5, 0.0, 0.25,
0.0, 0.0, 1.0, 0.0,
0.0, 0.0, 0.0, 1.0
);

mat4 end_portal_layer(float layer) {
    mat4 translate = mat4(
    1.0, 0.0, 0.0, 17.0 / layer,
    0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (GameTime * 1.5),
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    mat2 rotate = mat2_rotate_z(radians((layer * layer * 4321.0 + layer * 9.0) * 2.0));

    mat2 scale = mat2((4.5 - layer / 4.0) * 2.0);

    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}

vec3 hueShift(vec3 color, float hueAdjust){

    const vec3  kRGBToYPrime = vec3 (0.299, 0.587, 0.114);
    const vec3  kRGBToI      = vec3 (0.596, -0.275, -0.321);
    const vec3  kRGBToQ      = vec3 (0.212, -0.523, 0.311);

    const vec3  kYIQToR     = vec3 (1.0, 0.956, 0.621);
    const vec3  kYIQToG     = vec3 (1.0, -0.272, -0.647);
    const vec3  kYIQToB     = vec3 (1.0, -1.107, 1.704);

    float   YPrime  = dot (color, kRGBToYPrime);
    float   I       = dot (color, kRGBToI);
    float   Q       = dot (color, kRGBToQ);
    float   hue     = atan (Q, I);
    float   chroma  = sqrt (I * I + Q * Q);

    hue += hueAdjust;

    Q = chroma * sin (hue);
    I = chroma * cos (hue);

    vec3    yIQ   = vec3 (YPrime, I, Q);

    return vec3(dot (yIQ, kYIQToR), dot (yIQ, kYIQToG), dot (yIQ, kYIQToB));

}

out vec4 fragColor;

void main() {
    vec4 mask = texture(Sampler2, texCoord0);
    if (mask.a < 0.1) {
        discard;
    }


    vec3 color = textureProj(Sampler0, texProj0).rgb * COLORS[0];
    for (int i = 0; i < EndPortalLayers; i++) {
        color += textureProj(Sampler1, texProj0 * end_portal_layer(float(i + 1))).rgb * COLORS[i];
    }

    color = hueShift(color, GameTime * 1000.0);

    fragColor = vec4(color, mask.a);
}
