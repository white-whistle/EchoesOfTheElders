package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class VampireProjectileEntity extends ThrownItemEntity {
    public VampireProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public VampireProjectileEntity(World world, LivingEntity livingEntity) {
        super(ModEntities.VAMPIRE_PROJECTILE_ENTITY_TYPE, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }
}
