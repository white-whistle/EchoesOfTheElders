package com.bajookie.echoes_of_the_elders.system.Raid;

import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public record RaidWave(Text name, int level, int count, RaidSpawner spawner, RaidPositioner positioner) {
    static Random r = new Random();

    public ArrayList<UUID> spawnEntities(LivingEntity objective) {
        var world = objective.getWorld();
        var entities = new ArrayList<UUID>();

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

            entities.add(entity.getUuid());
        }

        return entities;
    }
}
