package com.bajookie.echoes_of_the_elders.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;

public interface IEmptyClick {
    boolean onEmptyClick(PlayerEntity user, ItemStack self, StackReference cursor);
}
