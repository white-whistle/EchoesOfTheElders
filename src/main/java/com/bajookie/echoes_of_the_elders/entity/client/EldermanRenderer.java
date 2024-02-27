package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.client.render.EffectLayer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class EldermanRenderer extends EndermanEntityRenderer {

    private static final Identifier TEXTURE_ELDER = new Identifier(MOD_ID, "textures/entity/elderman/elderman.png");
    public static final RenderLayer EYES = EffectLayer.getPortalMasked(new Identifier(MOD_ID, "textures/entity/elderman/elderman_portal.png"));

    public EldermanRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(EndermanEntity endermanEntity) {
        return TEXTURE_ELDER;
    }
}
