package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AuraUtil {
    public static final int RATE = 20;

    public static void applyAuraEffect(World world, Vec3d pos, StatusEffect effect, float range, int level) {
        if (world.getTime() % RATE == 0) {
            Box box = new Box(new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ())).expand(range);

            var entities = world.getNonSpectatingEntities(LivingEntity.class, box);

            for (LivingEntity entity : entities) {
                entity.addStatusEffect(new StatusEffectInstance(effect, RATE + 1, level, true, false));
            }

        }
    }
}
