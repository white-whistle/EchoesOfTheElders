package com.bajookie.biotech.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HomingFirework extends FireworkRocketEntity {
    public HomingFirework(World world, ItemStack stack, LivingEntity shooter) {
        super(world, stack, shooter);
    }

    @Override
    public void tick() {
        super.tick();
    }

}
