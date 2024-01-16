package com.bajookie.echoes_of_the_elders.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ShatterEffect extends StatusEffect {
    public ShatterEffect() {
        super(StatusEffectCategory.HARMFUL, 0xfffff);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.setInPowderSnow(true);
        super.applyUpdateEffect(entity, amplifier);
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
