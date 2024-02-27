package com.bajookie.echoes_of_the_elders.client.render;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;

public class ModShaderTypes {

    public static ShaderProgram renderTypeMaskedEndPortal;
    public static ShaderProgram renderTypeEntityTest;

    public static ShaderProgram getRenderTypeMaskedEndPortal() {
        return renderTypeMaskedEndPortal;
    }

    public static ShaderProgram getRenderTypeEntityTest() {
        return renderTypeEntityTest;
    }

    public static void init() {
        CoreShaderRegistrationCallback.EVENT.register((ctx) -> {
            ctx.register(new ModIdentifier("rendertype_end_portal_mask"), VertexFormats.POSITION, shaderProgram -> {
                renderTypeMaskedEndPortal = shaderProgram;
            });

            ctx.register(new ModIdentifier("rendertype_entity_test"), VertexFormats.POSITION, shaderProgram -> {
                renderTypeEntityTest = shaderProgram;
            });
        });
    }
}
