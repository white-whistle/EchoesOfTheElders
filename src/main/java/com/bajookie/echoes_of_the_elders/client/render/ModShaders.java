package com.bajookie.echoes_of_the_elders.client.render;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.minecraft.client.render.RenderPhase;

public class ModShaders {
    public static final RenderPhase.ShaderProgram END_PORTAL_MASK_PROGRAM = new RenderPhase.ShaderProgram(ModShaderTypes::getRenderTypeMaskedEndPortal);
    public static final RenderPhase.ShaderProgram ITEM_ENTITY_TRANSLUCENT_SIMPLE = new RenderPhase.ShaderProgram(ModShaderTypes::getRenderTypeItemEntityTranslucentSimple);

    public static void init() {
        EOTE.LOGGER.info("Init mod shaders");
    }
}
