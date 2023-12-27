package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.SpiritEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.9.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class SpiritItemEntityModel extends EntityModel<SpiritEntity> {
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public SpiritItemEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(0, 4).cuboid(-5.0F, 0.0F, -2.0F, 5.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        body.addChild("left_wing", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, -2.0F, 5.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(SpiritEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float k;

        boolean notMoving = entity.isOnGround() && entity.getVelocity().lengthSquared() < 1.0E-7;

        if (notMoving) {
            this.rightWing.yaw = -0.2618f;
            this.rightWing.roll = 0.0f;
            this.leftWing.pitch = 0.0f;
            this.leftWing.yaw = 0.2618f;
            this.leftWing.roll = 0.0f;
        } else {
            k = ageInTicks * 120.32113f * ((float) Math.PI / 180);
            this.rightWing.yaw = 0.0f;
            this.rightWing.roll = MathHelper.cos(k) * (float) Math.PI * 0.15f;
            this.leftWing.pitch = this.rightWing.pitch;
            this.leftWing.yaw = this.rightWing.yaw;
            this.leftWing.roll = -this.rightWing.roll;
        }

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}