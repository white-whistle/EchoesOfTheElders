package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.MonolookEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class MonolookEntityRenderer extends MobEntityRenderer<MonolookEntity,MonolookEntityModel> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID,"textures/entity/monolook.png");

    public MonolookEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MonolookEntityModel(context.getPart(ModModelLayers.MONOLOOK_LAYER)), 0.6f);
    }

    @Override
    public Identifier getTexture(MonolookEntity entity) {
        return TEXTURE;
    }
}
