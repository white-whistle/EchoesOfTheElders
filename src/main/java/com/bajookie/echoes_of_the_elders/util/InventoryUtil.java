package com.bajookie.echoes_of_the_elders.util;

import com.google.common.collect.Streams;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

    public static Stream<ItemStack> toStream(Inventory inventory) {
        return Streams.stream(new InventoryIterable(inventory));
    }

    @Nullable
    public static ItemStack find(Inventory inventory, Item item) {
        return find(inventory, s -> s.isOf(item));
    }

    public static class InventoryIterable implements Iterable<ItemStack> {

        public Inventory inventory;

        public InventoryIterable(Inventory inventory) {
            this.inventory = inventory;
        }

        @NotNull
        @Override
        public Iterator<ItemStack> iterator() {
            return new IteratorImpl(inventory);
        }

        private static class IteratorImpl implements Iterator<ItemStack> {
            private int index = 0;
            private final Inventory inventory;

            public IteratorImpl(Inventory inventory) {
                this.inventory = inventory;
            }

            @Override
            public boolean hasNext() {
                return index < inventory.size() - 1;
            }

            @Override
            public ItemStack next() {
                return inventory.getStack(index++);
            }
        }
    }
}
