package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.MonolookEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
public class MonolookEntityModel extends EntityModel<MonolookEntity> {
    private final ModelPart shape;
    private final ModelPart body;
    private final ModelPart legs;
    public MonolookEntityModel(ModelPart root) {
        this.shape = root.getChild("shape");
        this.body = this.shape.getChild("body");
        this.legs = this.shape.getChild("legs");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData shape = modelPartData.addChild("shape", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 14.0F, -2.0F));

        ModelPartData body = shape.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -18.0F, -3.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, -1.0F));

        ModelPartData legs = shape.addChild("legs", ModelPartBuilder.create().uv(12, 14).cuboid(3.0F, -10.0F, 2.0F, 0.0F, 7.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 14).cuboid(3.0F, -10.0F, -2.0F, 0.0F, 7.0F, 2.0F, new Dilation(0.0F))
                .uv(4, 14).cuboid(-3.0F, -10.0F, -2.0F, 0.0F, 7.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-3.0F, -10.0F, 2.0F, 0.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, -1.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(MonolookEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        shape.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}