package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.ZomBeeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ZomBeeRenderer extends MobEntityRenderer<ZomBeeEntity, ZomBeeModel<ZomBeeEntity>> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID,"textures/entity/zombee.png");

    public ZomBeeRenderer(EntityRendererFactory.Context context) {
        super(context, new ZomBeeModel<>(context.getPart(EntityModelLayers.BEE)), 0.4f);
    }
    @Override
    public Identifier getTexture(ZomBeeEntity beeEntity) {
        return TEXTURE;
    }
}
