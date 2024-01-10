package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.effects.DelayedEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method="onStatusEffectRemoved", at = @At("TAIL"))
    public void onEffectRemoved(StatusEffectInstance effect, CallbackInfo ci) {
        var entity = (LivingEntity)(Object)this;
        var world = entity.getWorld();

        if (!world.isClient()) {
            if (effect.getEffectType() instanceof DelayedEffect delayedEffect) {
                delayedEffect.onRemoved(effect, entity);
            }
        }
    }

}
