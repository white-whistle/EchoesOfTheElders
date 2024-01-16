package com.bajookie.echoes_of_the_elders.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class VampireProjectileModel extends EntityModel<Entity> {
    private final ModelPart body;
    public VampireProjectileModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
                .uv(2, 2).cuboid(-1.0F, -8.0F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -8.0F, -1.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(1, 1).cuboid(0.0F, -2.0F, 1.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(1, 1).cuboid(0.0F, -2.0F, -2.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(2, 2).cuboid(1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(2, 2).cuboid(-2.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

}
