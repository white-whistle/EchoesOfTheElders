package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public class InventoryUtil {

    public static void forEach(Inventory inventory, Consumer<ItemStack> stackConsumer) {
        for (int i = 0; i < inventory.size(); i++) {
            var stack = inventory.getStack(i);
            stackConsumer.accept(stack);
        }
    }

}
