package com.bajookie.echoes_of_the_elders.effects;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.function.BiConsumer;

public abstract class DelayedEffect extends StatusEffect {
    public DelayedEffect(StatusEffectCategory category) {
        super(category, 0x000000);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        super.onRemoved(attributeContainer);
    }

    public abstract void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity);

    public static DelayedEffect create(StatusEffectCategory category, BiConsumer<StatusEffectInstance, LivingEntity> biConsumer) {
        return new DelayedEffect(category) {
            @Override
            public void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity) {
                biConsumer.accept(effectInstance, entity);
            }
        };
    }
}
