package com.bajookie.echoes_of_the_elders.system.Raid;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.Random;

public interface RaidPositioner {
    Random r = new Random();

    BlockPos next(World world, LivingEntity objective, LivingEntity entity);

    static RaidPositioner random(float minDistance, float maxDistance) {
        return (world, objective, entity) -> {
            float mag = minDistance + ((maxDistance - minDistance) * r.nextFloat());
            float deg = r.nextFloat();
            int x = (int) (Math.sin(deg * Math.PI * 2) * mag) + objective.getBlockX();
            int z = (int) (Math.cos(deg * Math.PI * 2) * mag) + objective.getBlockZ();
            int y = world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z);

            return new BlockPos(x, y, z);
        };
    }
}
