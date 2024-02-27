package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.client.render.AdditionalArmorLayers;
import com.bajookie.echoes_of_the_elders.client.render.ItemStackCustomArmorTextures;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin {

    @Unique
    private EquipmentSlot currentSlot;

    @Unique
    private LivingEntity currentEntity;

    @Unique
    private ItemStack currentStack;

    @Inject(method = "renderArmor", at = @At("HEAD"))
    private void onRenderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model, CallbackInfo ci) {
        currentSlot = armorSlot;
        currentEntity = entity;
        currentStack = currentEntity.getEquippedStack(currentSlot);

        System.out.println("render armor!!");
    }

    @Inject(method = "renderArmorParts", at = @At("TAIL"))
    private void renderExtraArmorLayers(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, BipedEntityModel<LivingEntity> model, boolean secondTextureLayer, float red, float green, float blue, @Nullable String overlay, CallbackInfo ci) {
        var renderLayer = AdditionalArmorLayers.get(currentStack, currentSlot, currentEntity, secondTextureLayer ? 2 : 1);
        if (renderLayer != null) {
            var vertexConsumer = vertexConsumers.getBuffer(renderLayer);

            model.render(matrices, vertexConsumer, 0xF00000, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }

        System.out.println("render armor parts!");
    }

    @Inject(method = "getArmorTexture", at = @At("HEAD"), cancellable = true)
    private void getCustomArmorTexture(ArmorItem item, boolean secondLayer, String overlay, CallbackInfoReturnable<Identifier> cir) {
        var id = ItemStackCustomArmorTextures.get(currentStack, currentSlot, currentEntity, secondLayer ? 2 : 1);
        System.out.println("WTF");
        System.out.println(id);
        System.out.println(currentStack);
        if (id != null) {
            cir.setReturnValue(id);
        }
    }

}
