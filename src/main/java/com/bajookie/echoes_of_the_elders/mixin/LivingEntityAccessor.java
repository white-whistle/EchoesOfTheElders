package com.bajookie.echoes_of_the_elders.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Invoker("onStatusEffectRemoved")
    void invokeOnStatusEffectRemoved(StatusEffectInstance effect);
}
