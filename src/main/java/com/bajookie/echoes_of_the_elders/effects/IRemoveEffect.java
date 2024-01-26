package com.bajookie.echoes_of_the_elders.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public interface IRemoveEffect {
    void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity);
}
