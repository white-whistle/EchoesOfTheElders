package com.bajookie.echoes_of_the_elders.entity.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

import java.util.List;

// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class HaloModel extends BipedEntityModel<LivingEntity> {

    public HaloModel(ModelPart root) {
        super(root);
    }

    public static EntityModelLayerRegistry.TexturedModelDataProvider getTexturedModelData(float pitch, float yaw, float roll) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData Head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1047F, 0.0873F, 0.0F));
        ModelPartData HatPart_r1 = Head.addChild("HatPart_r1", ModelPartBuilder.create().uv(-24, 0).cuboid(-12.0F, 0.0F, -12.0F, 24.0F, 0.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 0.0F, pitch, yaw, roll));

        List.of(
                EntityModelPartNames.HAT,
                EntityModelPartNames.BODY,
                EntityModelPartNames.RIGHT_ARM,
                EntityModelPartNames.LEFT_ARM,
                EntityModelPartNames.RIGHT_LEG,
                EntityModelPartNames.LEFT_LEG
        ).forEach(p -> modelPartData.addChild(p, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F)));

        return () -> TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}