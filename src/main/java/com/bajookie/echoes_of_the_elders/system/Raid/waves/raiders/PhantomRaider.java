package com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.world.World;

public class PhantomRaider extends RaidEntityFeature<PhantomEntity>{
    public PhantomRaider( int minimumLevel, int step, int baseCount) {
        super(EntityType.PHANTOM, minimumLevel, step, baseCount);
    }

    @Override
    public PhantomEntity makeEntity(World world, int level) {
        return this.entityType.create(world);
    }

    @Override
    public void equipEntity(PhantomEntity entity, int level) {

    }

    @Override
    public void buffEntity(PhantomEntity entity, int level) {

    }
}
