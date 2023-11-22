package com.bajookie.biotech.entity.client;

import com.bajookie.biotech.entity.custom.FlowerDefenseEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class FlowerDefenseRenderer extends MobEntityRenderer<FlowerDefenseEntity,FlowerDefenseModel<FlowerDefenseEntity>> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID,"textures/entity/flower_defense.png");

    public FlowerDefenseRenderer(EntityRendererFactory.Context context) {
        super(context, new FlowerDefenseModel<>(context.getPart(ModModelLayers.FLOWER_DEFENSE_LAYER)),0.6f); // float is for the shadow
    }

    @Override
    public Identifier getTexture(FlowerDefenseEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(FlowerDefenseEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()){
            matrixStack.scale(0.5f,0.5f,0.5f);
        } else {
            matrixStack.scale(1f,1f,1f);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
