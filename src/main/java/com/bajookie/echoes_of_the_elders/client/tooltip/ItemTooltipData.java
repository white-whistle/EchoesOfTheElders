package com.bajookie.echoes_of_the_elders.client.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;

public record ItemTooltipData(ItemStack stack) implements TooltipData {
}
