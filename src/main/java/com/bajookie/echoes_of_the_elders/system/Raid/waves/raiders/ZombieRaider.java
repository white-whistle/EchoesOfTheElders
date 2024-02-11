package com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders;

import com.bajookie.echoes_of_the_elders.system.Raid.waves.WaveFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public class ZombieRaider extends RaidEntityFeature<ZombieEntity>{
    public ZombieRaider(int minimumLevel, int step, int baseCount) {
        super(EntityType.ZOMBIE, minimumLevel, step, baseCount);
    }


    @Override
    public ZombieEntity makeEntity(World world, int level) {
        var entity = this.entityType.create(world);
        this.equipEntity(entity,level);
        return entity;
    }

    @Override
    public void equipEntity(ZombieEntity entity,int level) {
        WaveFeatures.EntityToolFeature.ZOMBIE_HAND.equip(entity,level);
        WaveFeatures.EntityToolFeature.HELMETS.equip(entity,level);
        WaveFeatures.EntityToolFeature.CHEST_PLATES.equip(entity,level);
        WaveFeatures.EntityToolFeature.LEGGINGS.equip(entity,level);
        WaveFeatures.EntityToolFeature.BOOTS.equip(entity,level);
    }

    @Override
    public void buffEntity(ZombieEntity entity,int level) {

    }
}
