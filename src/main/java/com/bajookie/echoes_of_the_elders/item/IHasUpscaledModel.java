package com.bajookie.echoes_of_the_elders.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public interface IHasUpscaledModel {
    default boolean shouldUseUpscaledModel(ItemStack itemStack) {
        return true;
    }

    static Identifier getUpscaledItemModel(Item item) {
        Identifier identifier = Registries.ITEM.getId(item);

        return identifier.withSuffixedPath("_x32");
    }
}
