package com.bajookie.echoes_of_the_elders.effects;

import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

import java.util.Random;

public class StarfallEffect extends StatusEffect {

    protected StarfallEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        int duration = entity.getStatusEffect(ModEffects.STARFALL_EFFECT).getDuration();
        Random r = new Random();
        if (duration <=30){
            ((ServerWorld)entity.getWorld()).spawnParticles(ModParticles.STARFALL_TRAIL_PARTICLE,entity.getX(),entity.getHeight()+entity.getY()+duration*1.2,entity.getZ(),2,r.nextInt(-8,8)*0.01,r.nextInt(8)*0.05,r.nextInt(-8,8)*0.05,0.1D);
            ((ServerWorld)entity.getWorld()).spawnParticles(ModParticles.STARFALL_STAR_PARTICLE,entity.getX(),entity.getHeight()-0.1+entity.getY()+duration*1.2,entity.getZ(),1,0,0,0,0.0D);
            if (duration<2){
                entity.damage(entity.getWorld().getDamageSources().create(DamageTypes.MAGIC),10f);
            }
        }
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        int duration = entity.getStatusEffect(ModEffects.STARFALL_EFFECT).getDuration();

        super.onApplied(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
