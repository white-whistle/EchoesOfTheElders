package com.bajookie.echoes_of_the_elders.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class NoGravityEffect extends StatusEffect implements IRemoveEffect {
    protected NoGravityEffect() {
        super(StatusEffectCategory.HARMFUL,0x000000);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }
    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if (entity.hasNoGravity()){
            entity.removeStatusEffect(this);
        } else {
            entity.setNoGravity(true);
        }
    }

    public static boolean tryApply(LivingEntity entity){
        return !entity.hasNoGravity();
    }

    @Override
    public void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity) {
        entity.setNoGravity(false);
    }
}
