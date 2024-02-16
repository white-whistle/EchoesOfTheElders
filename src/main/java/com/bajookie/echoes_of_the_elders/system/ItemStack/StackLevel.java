package com.bajookie.echoes_of_the_elders.system.ItemStack;

import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.item.ItemStack;

public class StackLevel {

    private static class Keys {
        public static final String STACK_LEVEL = ModIdentifier.string("stack_level");
    }

    public static int get(ItemStack itemStack) {
        var nbt = itemStack.getNbt();
        if (nbt == null) return 1;

        return nbt.getInt(Keys.STACK_LEVEL);
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
        return get(itemStack) / (float) (getMax(itemStack) - 1);
    }

    public static ItemStack set(ItemStack itemStack, int tier) {
        var nbt = itemStack.getOrCreateNbt();

        nbt.putInt(Keys.STACK_LEVEL, tier);

        return itemStack;
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
