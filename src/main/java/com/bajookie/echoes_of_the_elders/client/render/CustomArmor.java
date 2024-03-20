package com.bajookie.echoes_of_the_elders.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomArmor implements ArmorRenderer {
    private final List<Part> armorParts;
    private BipedEntityModel<LivingEntity> innerArmor;
    private BipedEntityModel<LivingEntity> outerArmor;
    private BipedEntityModel<LivingEntity> slimInnerArmor;
    private BipedEntityModel<LivingEntity> slimOuterArmor;
    private boolean wasInit = false;

    static abstract class PartInitCtx {
        public final List<Part> parts;

        public PartInitCtx(List<Part> parts) {
            this.parts = parts;
        }

        public abstract BipedEntityModel<LivingEntity> getInnerArmor(LivingEntity entity);

        public abstract BipedEntityModel<LivingEntity> getOuterArmor(LivingEntity entity);
    }

    public CustomArmor(Consumer<PartInitCtx> createParts) {
        this.armorParts = new ArrayList<>();

        var ctx = new PartInitCtx(armorParts) {
            @Override
            public BipedEntityModel<LivingEntity> getOuterArmor(LivingEntity entity) {
                return CustomArmor.this.getOuterArmor(entity);
            }

            @Override
            public BipedEntityModel<LivingEntity> getInnerArmor(LivingEntity entity) {
                return CustomArmor.this.getInnerArmor(entity);
            }
        };

        createParts.accept(ctx);
    }

    public static boolean isSlim(LivingEntity livingEntity) {
        if (livingEntity instanceof ClientPlayerEntity clientPlayerEntity) {
            return clientPlayerEntity.getSkinTextures().model() == SkinTextures.Model.SLIM;
        }

        return false;
    }

    public static void setVisible(BipedEntityModel<LivingEntity> bipedModel, EquipmentSlot slot) {
        bipedModel.setVisible(false);
        switch (slot) {
            case HEAD -> {
                bipedModel.head.visible = true;
                bipedModel.hat.visible = true;
            }
            case CHEST -> {
                bipedModel.body.visible = true;
                bipedModel.rightArm.visible = true;
                bipedModel.leftArm.visible = true;
            }
            case LEGS -> {
                bipedModel.body.visible = true;
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
            }
            case FEET -> {
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (!wasInit) {
            innerArmor = new BipedEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_INNER_ARMOR));
            outerArmor = new BipedEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));
            slimInnerArmor = new BipedEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_SLIM_INNER_ARMOR));
            slimOuterArmor = new BipedEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR));

            wasInit = true;
        }

        for (Part part : armorParts) {
            part.render(this, matrices, vertexConsumers, stack, entity, slot, light, contextModel);
        }
    }

    public BipedEntityModel<LivingEntity> getInnerArmor(LivingEntity entity) {
        return CustomArmor.isSlim(entity) ? this.slimInnerArmor : this.innerArmor;
    }

    public BipedEntityModel<LivingEntity> getOuterArmor(LivingEntity entity) {
        return CustomArmor.isSlim(entity) ? this.slimOuterArmor : this.outerArmor;
    }

    public static class Part {
        Function<ItemStack, RenderLayer> renderLayerGetter;
        Function<LivingEntity, BipedEntityModel<LivingEntity>> modelGetter;

        public Part(Function<LivingEntity, BipedEntityModel<LivingEntity>> modelGetter, Function<ItemStack, RenderLayer> renderLayerGetter) {
            this.renderLayerGetter = renderLayerGetter;
            this.modelGetter = modelGetter;
        }

        public void render(CustomArmor customArmor, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
            if (!this.shouldRender(customArmor, matrices, vertexConsumers, stack, entity, slot, light, contextModel))
                return;

            var renderLayer = renderLayerGetter.apply(stack);
            var vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, renderLayer, false, stack.hasGlint());
            var model = modelGetter.apply(entity);

            contextModel.copyBipedStateTo(model);

            CustomArmor.setVisible(model, slot);

            var alpha = this.getAlpha();
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, alpha);
        }

        public float getAlpha() {
            return 1;
        }

        public boolean shouldRender(CustomArmor customArmor, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
            return true;
        }

    }
}
