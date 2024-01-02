package com.bajookie.echoes_of_the_elders.entity.client;

import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class EldermanRenderer extends EndermanEntityRenderer {

    private static final Identifier TEXTURE_ELDER = new Identifier(MOD_ID,"textures/entity/elderman_entity.png");
    public EldermanRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(EndermanEntity endermanEntity) {
        return TEXTURE_ELDER;
    }
}
