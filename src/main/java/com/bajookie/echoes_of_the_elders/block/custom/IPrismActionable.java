package com.bajookie.echoes_of_the_elders.block.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface IPrismActionable {
    boolean onPrism(ItemStack stack, PlayerEntity user, World world, BlockPos blockPos, Direction side);
}
