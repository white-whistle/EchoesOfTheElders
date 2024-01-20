package com.bajookie.echoes_of_the_elders.system.Raid;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public interface RaidSpawner {
    LivingEntity getRaider(World world);

    static <T extends LivingEntity> RaidSpawner of(EntityType<T> entityType) {
        return entityType::create;
    }
}
