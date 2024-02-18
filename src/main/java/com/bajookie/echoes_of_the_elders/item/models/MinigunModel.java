package com.bajookie.echoes_of_the_elders.item.models;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

import java.util.Optional;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class MinigunModel extends Model {
    public static final Identifier TEXTURE = new Identifier(EOTE.MOD_ID, "textures/item/ancient_minigun_new.png");
    private final ModelPart barr;
    private final ModelPart r_ind;
    private final ModelPart root;

    public MinigunModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root.getChild("root");
        this.barr = this.root.getChild("barr");
        this.r_ind = this.root.getChild("r_ind");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(20, 11).cuboid(2.5F, -2.5F, -5.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(10, 11).cuboid(-0.5F, -4.5F, 1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -3.5F, 0.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-2.5F, -1.5F, -2.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -2.5F, -7.0F, 3.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 16.5F, 6.0F));

        ModelPartData r_ind = root.addChild("r_ind", ModelPartBuilder.create().uv(0, 5).cuboid(-1.0F, -3.0F, -7.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 3).cuboid(-1.0F, -4.0F, -7.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(3, 4).cuboid(-1.0F, -3.0F, -3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -0.5F, 0.0F));

        ModelPartData barr = root.addChild("barr", ModelPartBuilder.create().uv(0, 19).cuboid(-0.5F, 0.5F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(12, 2).cuboid(-0.5F, -1.5F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(10, 11).cuboid(-1.5F, -0.5F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(0.5F, -0.5F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -1.0F, -7.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {

        //TODO alpha Gradient

        barr.roll = barr.roll + 0.03f;
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public static void applyTranslates(ModelTransformationMode renderMode,MatrixStack matrices){
        if (renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND){
            matrices.translate((1f/16f),-(18f/16f),-(1f/16f));
        }
        else if (renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND){
            matrices.translate((1f/16f),-(13f/16f),-(5f/16f));
        }
    }
}
