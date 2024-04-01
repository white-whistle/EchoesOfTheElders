package com.bajookie.echoes_of_the_elders.entity.custom;

import net.minecraft.world.World;

public interface ICustomProjectileCrit {

    void spawnCritParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ);
}
