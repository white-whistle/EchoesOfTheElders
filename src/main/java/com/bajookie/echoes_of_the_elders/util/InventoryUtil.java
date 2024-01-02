package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class InventoryUtil {

    public static void forEach(Inventory inventory, Consumer<ItemStack> stackConsumer) {
        for (int i = 0; i < inventory.size(); i++) {
            var stack = inventory.getStack(i);
            stackConsumer.accept(stack);
        }
    }

    @Nullable
    public static ItemStack find(Inventory inventory, Predicate<ItemStack> predicate) {
        for (int i = 0; i < inventory.size(); i++) {
            var stack = inventory.getStack(i);
            if (predicate.test(stack)) return stack;
        }
        return null;
    }

    @Nullable
    public static ItemStack find(Inventory inventory, Item item) {
        return find(inventory, s -> s.isOf(item));
    }

}
