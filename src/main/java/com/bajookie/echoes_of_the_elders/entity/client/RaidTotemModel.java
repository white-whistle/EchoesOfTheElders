package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.RaidTotemEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class RaidTotemModel extends EntityModel<RaidTotemEntity> {
    private final ModelPart bb_main;
    private final ModelPart bb_overlay;
    private final ModelPart bb_underlay;
    private float healthProgress = 1;

    public RaidTotemModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
        this.bb_overlay = root.getChild("bb_overlay");
        this.bb_underlay = root.getChild("bb_underlay");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -60.0F, -4.0F, 8.0F, 44.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData bb_overlay = modelPartData.addChild("bb_overlay", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -60.0F, -4.0F, 8.0F, 44.0F, 8.0F, new Dilation(-0.2F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData bb_underlay = modelPartData.addChild("bb_underlay", ModelPartBuilder.create().uv(64, 0).cuboid(-4.0F, -60.0F, -4.0F, 8.0F, 44.0F, 8.0F, new Dilation(-0.4F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 96, 64);
    }

    @Override
    public void setAngles(RaidTotemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        var yaw = (float) Math.toRadians((ageInTicks * 0.5 * Math.PI * 2) % 360);
        this.bb_main.yaw = yaw;
        this.bb_overlay.yaw = yaw;
        this.bb_underlay.yaw = yaw;

        healthProgress = Math.min(entity.getHealth() / entity.getMaxHealth(), 1);
        // bb_overlay.scale(new Vector3f(1, healthProgress, 1));
        bb_overlay.yScale = healthProgress;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {

        bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        bb_underlay.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        matrices.push();
        matrices.translate(0, (1 - healthProgress) * -1, 0);
        bb_overlay.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.pop();

    }
}