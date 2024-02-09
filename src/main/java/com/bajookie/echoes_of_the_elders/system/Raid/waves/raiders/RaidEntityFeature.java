package com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.List;

public abstract class RaidEntityFeature<T extends LivingEntity> {
    public EntityType<T> entityType;
    public int minimumLevel;
    public int step;
    public int baseCount;

    public RaidEntityFeature(EntityType<T> type, int minimumLevel, int step, int baseCount) {
        this.entityType = type;
        this.minimumLevel = minimumLevel;
        this.step = step;
        this.baseCount = baseCount;
    }

    public boolean canApply(int level) {
        return this.minimumLevel <= level;
    }

    public abstract T makeEntity(World world, int level);

    public abstract void equipEntity(T entity, int level);

    public int getCount(int level) {
        return (level - this.minimumLevel) * step + baseCount;
    }

    public abstract void buffEntity(T entity, int level);

}
