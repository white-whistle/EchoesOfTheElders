package com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.world.World;

public class WitchRaider extends RaidEntityFeature<WitchEntity>{
    public WitchRaider(int minimumLevel, int step, int baseCount) {
        super(EntityType.WITCH, minimumLevel, step, baseCount);
    }

    @Override
    public WitchEntity makeEntity(World world, int level) {
        var entity = this.entityType.create(world);
        this.equipEntity(entity,level);
        return entity;
    }

    @Override
    public void equipEntity(WitchEntity entity, int level) {

    }

    @Override
    public void buffEntity(WitchEntity entity, int level) {

    }
}
