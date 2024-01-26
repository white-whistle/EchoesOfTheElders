package com.bajookie.echoes_of_the_elders.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ShockEffect extends StatusEffect {
    public ShockEffect() {
        super(StatusEffectCategory.NEUTRAL,0x000000);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

}
