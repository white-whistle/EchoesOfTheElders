package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Inject(method = "isShaking", at = @At("HEAD"), cancellable = true)
    public void isShaking(T entity, CallbackInfoReturnable<Boolean> info) {
        if (entity.hasStatusEffect(ModEffects.ELECTRIC_STUN_EFFECT)){
            info.setReturnValue(true);
        }
    }
}
