package com.bajookie.echoes_of_the_elders.system.ItemStack;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static com.bajookie.echoes_of_the_elders.system.ItemStack.CustomItemNbt.STACK_LEVEL;

public class StackLevel {

    public static int get(ItemStack itemStack) {
        return STACK_LEVEL.get(itemStack);
    }

    public static int compare(ItemStack a, ItemStack b) {
        return get(a) - get(b);
    }

    @Nullable
    public static ItemStack getBest(Stream<ItemStack> stream) {
        return stream.max(StackLevel::compare).orElse(null);
    }

    public static int getMax(ItemStack itemStack) {
        var item = itemStack.getItem();
        if (item instanceof IArtifact artifact) {
            return artifact.getArtifactMaxStack();
        }
        return 1;
    }

    public static boolean isMaxed(ItemStack itemStack) {
        return get(itemStack) >= getMax(itemStack);
    }

    public static float getStackProgress(ItemStack itemStack) {
        return (get(itemStack) - 1) / (float) (getMax(itemStack) - 1);
    }

    public static ItemStack set(ItemStack itemStack, int tier) {
        return STACK_LEVEL.set(itemStack, tier);
    }

    public static void raise(ItemStack itemStack, int amt) {
        var tier = get(itemStack);

        set(itemStack, tier + amt);
    }

    public static void decrement(ItemStack itemStack, int amt) {
        var tier = get(itemStack);

        set(itemStack, tier - amt);

        if (get(itemStack) <= 0) itemStack.decrement(1);
    }
}
