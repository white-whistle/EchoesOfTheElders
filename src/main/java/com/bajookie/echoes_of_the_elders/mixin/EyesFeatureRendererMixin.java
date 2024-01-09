package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.entity.client.EldermanRenderer;
import com.bajookie.echoes_of_the_elders.entity.custom.EldermanEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EyesFeatureRenderer.class)
public class EyesFeatureRendererMixin<T extends Entity, M extends EntityModel<T>> {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/EyesFeatureRenderer;getEyesTexture()Lnet/minecraft/client/render/RenderLayer;"))
    public RenderLayer getTextureProxy(EyesFeatureRenderer<T, M> instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity instanceof EldermanEntity) {
            return EldermanRenderer.EYES;
        }

        return instance.getEyesTexture();
    }

}
