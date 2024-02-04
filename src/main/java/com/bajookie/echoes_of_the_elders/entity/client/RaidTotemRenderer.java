package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.RaidTotemEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class RaidTotemRenderer extends MobEntityRenderer<RaidTotemEntity, RaidTotemModel> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/raid_totem.png");


    public RaidTotemRenderer(EntityRendererFactory.Context context) {
        super(context, new RaidTotemModel(context.getPart(ModModelLayers.RAID_TOTEM_LAYER)), 0.6f); // float is for the shadow
    }

    @Override
    public Identifier getTexture(RaidTotemEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(RaidTotemEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }
        var age = mobEntity.age + g;
        var offsetY = (float) (Math.sin((age / 100) * Math.PI * 2)) * 0.5f;

        matrixStack.push();
        matrixStack.translate(0, offsetY, 0);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);


        matrixStack.pop();
    }
}
