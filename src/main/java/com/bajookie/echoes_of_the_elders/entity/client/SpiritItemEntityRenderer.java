package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.SpiritEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class SpiritItemEntityRenderer extends MobEntityRenderer<SpiritEntity, SpiritItemEntityModel> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/flying_item_entity.png");

    public SpiritItemEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SpiritItemEntityModel(context.getPart(ModModelLayers.SPIRIT_ENTITY_LAYER)), 0.6f);

        this.addFeature(new SpiritItemEntityHeldItemFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(SpiritEntity entity) {
        return TEXTURE;
    }

    static class SpiritItemEntityHeldItemFeatureRenderer extends VillagerHeldItemFeatureRenderer<SpiritEntity, SpiritItemEntityModel> {
        private final HeldItemRenderer heldItemRenderer;

        public SpiritItemEntityHeldItemFeatureRenderer(FeatureRendererContext<SpiritEntity, SpiritItemEntityModel> context, HeldItemRenderer heldItemRenderer) {
            super(context, heldItemRenderer);
            this.heldItemRenderer = heldItemRenderer;
        }

        @Override
        public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SpiritEntity livingEntity, float f, float g, float h, float j, float k, float l) {
            matrixStack.push();
            matrixStack.translate(0.0f, 1.55f, -0.1f);
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90f));
            ItemStack itemStack = ((LivingEntity) livingEntity).getEquippedStack(EquipmentSlot.MAINHAND);
            this.heldItemRenderer.renderItem(livingEntity, itemStack, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }
    }
}
