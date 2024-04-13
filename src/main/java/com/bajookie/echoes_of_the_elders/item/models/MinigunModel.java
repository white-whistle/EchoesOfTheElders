package com.bajookie.echoes_of_the_elders.item.models;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.TextureKey;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.joml.Math;
import org.joml.Quaternionf;

import java.util.Optional;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class MinigunModel extends Model {
    public static final Identifier TEXTURE = new Identifier(EOTE.MOD_ID, "textures/item/ancient_minigun_new.png");
    private final ModelPart barr;
    private final ModelPart r_ind;
    private final ModelPart bone;
    private final ModelPart body;
    private float rollSpeed = 0f;

    public MinigunModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.bone = root.getChild("bone");
        this.body = this.bone.getChild("body");
        this.barr = this.bone.getChild("barr");
        this.r_ind = this.bone.getChild("r_ind");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = bone.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -4.0F, -1.0F, 3.0F, 3.0F, 7.0F, new Dilation(0.0F))
                .uv(20, 11).cuboid(1.0F, -4.0F, 1.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(10, 11).cuboid(-2.0F, -6.0F, 7.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.0F, -2.0F, 6.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.5F, -5.0F, 6.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-4.0F, -3.0F, 4.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData r_ind = bone.addChild("r_ind", ModelPartBuilder.create().uv(0, 5).cuboid(-2.0F, -5.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 3).cuboid(-2.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(3, 4).cuboid(-2.0F, -5.0F, 3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData barr = bone.addChild("barr", ModelPartBuilder.create().uv(0, 19).cuboid(-0.5F, 0.5F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(12, 2).cuboid(-0.5F, -1.5F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(10, 11).cuboid(-1.5F, -0.5F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(0.5F, -0.5F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -2.5F, -1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {

        // TODO alpha Gradient
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        float a = 0;
        if (player != null) {
            Item item = player.getMainHandStack().getItem();
            // TODO: enable after release
            // if (item == ModItems.ANCIENT_MINIGUN){
            //     if (player.isUsingItem() && !player.getItemCooldownManager().isCoolingDown(item)){
            //         if (this.rollSpeed <=0.045f){
            //             this.rollSpeed =this.rollSpeed+ 0.0001f;
            //         }
            //     } else {
            //         if (this.rollSpeed >0.004f) {
            //             this.rollSpeed =this.rollSpeed - 0.00007f;
            //         } else {
            //             if (Math.sin(this.barr.roll) >=0.9995 || Math.sin(this.barr.roll) <=-0.9995 || (Math.sin(this.barr.roll) <=0.001 && Math.sin(this.barr.roll) >=-0.001)){
            //                 this.rollSpeed =0;
            //                 this.barr.roll =0;
            //             }
            //         }
            //         if (this.rollSpeed <0f){
            //                this.rollSpeed =0;
            //         }
            //     }
            //     if (this.rollSpeed == 0){
            //         this.barr.roll = 0;
            //     }
            //     this.barr.roll += this.rollSpeed;
            //     ItemStack stack = player.getMainHandStack();
            //     a =0.5f * ((float)stack.getDamage())/stack.getMaxDamage();
            // }
        }
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        barr.render(matrices, vertexConsumer, light, overlay, red, green - a, blue - a, alpha);
        r_ind.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public static void applyTranslates(ModelTransformationMode renderMode, MatrixStack matrices) {
        if (renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND) {
            matrices.translate((1f / 16f), -(0f / 16f), -(1f / 16f));
        } else if (renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND) {
            matrices.translate((1f / 16f), (5f / 16f), -(5f / 16f));
        }
    }
}
