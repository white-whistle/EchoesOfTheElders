package com.bajookie.echoes_of_the_elders.item.ability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class Ability {

    public abstract boolean cast(World world, PlayerEntity user, ItemStack itemStack, boolean ignoreCooldown);

}
