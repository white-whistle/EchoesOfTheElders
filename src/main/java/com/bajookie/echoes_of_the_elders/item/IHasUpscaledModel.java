package com.bajookie.echoes_of_the_elders.item;

import net.minecraft.item.ItemStack;

public interface IHasUpscaledModel {
    default boolean shouldUseUpscaledModel(ItemStack itemStack) {
        return true;
    }
}
