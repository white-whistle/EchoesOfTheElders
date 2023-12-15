package com.bajookie.echoes_of_the_elders.item;

import net.minecraft.item.ItemStack;

public interface ICooldownReduction {
    float getCooldownReductionPercentage(ItemStack stack);

    String cooldownInstanceId(ItemStack stack);
}
