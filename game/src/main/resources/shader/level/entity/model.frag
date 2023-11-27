#version 330

in vec4 color;
in vec2 uv;

uniform sampler2D u_texture;
uniform float u_skyBrightness;

void main(){
    vec4 color = color * vec4(vec3(7.0 / 15.0), 1) * texture(u_texture, uv);
    if(color.a <= 0)
        discard;

    color.rgb *= u_skyBrightness;

    gl_FragColor = color;
}
