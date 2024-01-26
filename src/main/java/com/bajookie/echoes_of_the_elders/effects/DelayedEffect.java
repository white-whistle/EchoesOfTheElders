package com.bajookie.echoes_of_the_elders.effects;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class DelayedEffect extends StatusEffect implements IRemoveEffect {
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

    public static DelayedEffect create(StatusEffectCategory category, BiConsumer<StatusEffectInstance, LivingEntity> biConsumer) {
        return new DelayedEffect(category) {
            @Override
            public void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity) {
                biConsumer.accept(effectInstance, entity);
            }
        };
    }
    public static DelayedEffect create(StatusEffectCategory category, BiConsumer<StatusEffectInstance, LivingEntity> biConsumer, BiConsumer<LivingEntity,Integer> onApplyConsumer){
        return new DelayedEffect(category) {
            @Override
            public void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity) {
                biConsumer.accept(effectInstance, entity);
            }

            @Override
            public void onApplied(LivingEntity entity, int amplifier) {
                onApplyConsumer.accept(entity,amplifier);
                super.onApplied(entity, amplifier);
            }
        };
    }
}
