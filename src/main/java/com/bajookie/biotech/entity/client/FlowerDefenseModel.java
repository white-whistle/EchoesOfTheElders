package com.bajookie.biotech.entity.client;

import com.bajookie.biotech.entity.custom.FlowerDefenseEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class FlowerDefenseModel<T extends FlowerDefenseEntity> extends SinglePartEntityModel<T> {
	private final ModelPart bone;
	private final ModelPart head;
	public FlowerDefenseModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.head = this.bone.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 12).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 6).cuboid(-1.0F, -3.0F, 1.0F, 2.0F, 1.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-1.0F, -3.0F, -6.0F, 2.0F, 1.0F, 5.0F, new Dilation(0.0F))
		.uv(9, 6).cuboid(-6.0F, -3.0F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(9, 0).cuboid(1.0F, -3.0F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = bone.addChild("head", ModelPartBuilder.create().uv(8, 12).cuboid(-0.5F, -9.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(0, 6).cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, -0.5F, -0.4363F, 0.0F, 0.0F));

		ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(8, 3).cuboid(0.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -8.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		ModelPartData cube_r3 = head.addChild("cube_r3", ModelPartBuilder.create().uv(8, 4).cuboid(-2.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -8.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		ModelPartData cube_r4 = head.addChild("cube_r4", ModelPartBuilder.create().uv(0, 2).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 0.5F, 0.4363F, -1.1345F, 0.0F));

		ModelPartData cube_r5 = head.addChild("cube_r5", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, -0.5F, -0.4363F, -1.1345F, 0.0F));

		ModelPartData cube_r6 = head.addChild("cube_r6", ModelPartBuilder.create().uv(0, 8).cuboid(-2.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -8.0F, 0.0F, -0.7363F, -0.9639F, 0.8345F));

		ModelPartData cube_r7 = head.addChild("cube_r7", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -8.0F, 0.0F, 0.7363F, -0.9639F, -0.8345F));

		ModelPartData cube_r8 = head.addChild("cube_r8", ModelPartBuilder.create().uv(7, 0).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 0.5F, 0.4363F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(FlowerDefenseEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return bone;
	}
}