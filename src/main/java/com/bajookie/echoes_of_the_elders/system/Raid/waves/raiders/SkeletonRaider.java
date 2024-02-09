package com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class SkeletonRaider extends RaidEntityFeature<SkeletonEntity>{
    public SkeletonRaider(int minimumLevel, int step, int baseCount) {
        super(EntityType.SKELETON, minimumLevel, step, baseCount);
    }

    @Override
    public SkeletonEntity makeEntity(World world, int level) {
        var entity = this.entityType.create(world);
        this.equipEntity(entity,level);
        return entity;
    }

    @Override
    public void equipEntity(SkeletonEntity entity,int level) {

    }

    @Override
    public void buffEntity(SkeletonEntity entity,int level) {

    }
}
