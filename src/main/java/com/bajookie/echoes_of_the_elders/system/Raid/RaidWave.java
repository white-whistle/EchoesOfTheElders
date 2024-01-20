package com.bajookie.echoes_of_the_elders.system.Raid;

import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;

import java.util.Random;

public record RaidWave(int level, int count, RaidSpawner spawner, RaidPositioner positioner) {
    static Random r = new Random();

    public int spawnEntities(LivingEntity objective) {
        var world = objective.getWorld();

        for (int i = 0; i < count; i++) {
            var entity = spawner.getRaider(world);
            var pos = positioner.next(world, objective, entity);

            entity.setPos(pos.getX() + r.nextFloat(), pos.getY(), pos.getZ() + r.nextFloat());

            world.spawnEntity(entity);

            if (entity instanceof MobEntity mobEntity) {
                mobEntity.setTarget(objective);
                mobEntity.setPersistent();
            }

            ModCapabilities.RAID_ENEMY.attach(entity, e -> {
                e.setRaidTarget(objective);
            });

            // entity.refreshPositionAndAngles(pos, 0 ,0);
        }

        return count;
    }
}
