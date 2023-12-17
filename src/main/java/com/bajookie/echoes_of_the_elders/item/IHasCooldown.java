package com.bajookie.echoes_of_the_elders.item;

import net.minecraft.item.ItemStack;

public interface IHasCooldown {
    int getCooldown(ItemStack itemStack);

    default int getMinCooldown() {
        return 1;
    }

    default boolean canReduceCooldown() {
        return true;
    }
}
