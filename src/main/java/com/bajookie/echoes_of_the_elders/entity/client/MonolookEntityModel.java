package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.MonolookEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class MonolookEntityModel extends EntityModel<MonolookEntity> {
    private final ModelPart entity;
    private final ModelPart body;
    private final ModelPart tentacles;
    private final ModelPart eye_overlay;
    public MonolookEntityModel(ModelPart root) {
        this.entity = root.getChild("entity");
        this.body = entity.getChild("body");
        this.eye_overlay = entity.getChild("eye_overlay");
        this.tentacles = entity.getChild("tentacles");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData entity = modelPartData.addChild("entity", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 22.0F, 0.0F));

        ModelPartData body = entity.addChild("body", ModelPartBuilder.create().uv(0, 5).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-3.0F, -6.0F, -1.0F, 6.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(8, 10).cuboid(-1.0F, -6.0F, 2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-2.0F, -6.0F, -2.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 5).cuboid(1.0F, -6.0F, -2.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData tentacles = entity.addChild("tentacles", ModelPartBuilder.create().uv(0, 15).cuboid(-2.0F, -3.0F, 0.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(12, 13).cuboid(1.0F, -3.0F, 0.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData eye_overlay = entity.addChild("eye_overlay", ModelPartBuilder.create().uv(12, 5).cuboid(-1.0F, -6.0F, -1.01F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        entity.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(MonolookEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}