precision mediump float;

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

const int EndPortalLayers=16;

uniform vec2 u_resolution;
uniform vec2 u_mouse;
uniform float u_time;

uniform vec3 COLORS[16];

const mat4 SCALE_TRANSLATE=mat4(
	.5,0.,0.,.25,
	0.,.5,0.,.25,
	0.,0.,1.,0.,
	0.,0.,0.,1.
);

mat2 mat2_rotate_z(float radians){
	return mat2(
		cos(radians),-sin(radians),
		sin(radians),cos(radians)
	);
}

mat4 end_portal_layer(float layer){
	mat4 translate=mat4(
		1.,0.,0.,17./layer,
		0.,1.,0.,(2.+layer/1.5)*((u_time/1000.)*1.5),
		0.,0.,1.,0.,
		0.,0.,0.,1.
	);
	
	mat2 rotate=mat2_rotate_z(radians((layer*layer*4321.+layer*9.)*2.));
	
	mat2 scale=mat2((4.5-layer/4.)*2.);
	
	return mat4(scale*rotate)*translate*SCALE_TRANSLATE;
}

void main(){
	vec2 st=gl_FragCoord.xy/u_resolution.xy;
	st.x*=u_resolution.x/u_resolution.y;
	
	vec3 color=texture2D(Sampler0,st).rgb*COLORS[0];
	
	for(int i=0;i<EndPortalLayers;i++){
		color+=texture2D(Sampler1,(vec4(st,1.,1.)*end_portal_layer(float(i+1))).rg).rgb*COLORS[i];
	}
	
	gl_FragColor=vec4(color,1.);
}
