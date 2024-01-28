package com.bajookie.echoes_of_the_elders.system.ItemStack;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.item.ItemStack;

public class Tier {

    private static class Keys {
        public static final String TIER = ModIdentifier.string("tier");
    }

    public static int get(ItemStack itemStack) {
        var nbt = itemStack.getNbt();
        if (nbt == null) return 0;

        return nbt.getInt(Keys.TIER);
    }

    public static void set(ItemStack itemStack, int tier) {
        var nbt = itemStack.getOrCreateNbt();

        nbt.putInt(Keys.TIER, tier);
    }

    public static void raise(ItemStack itemStack, int amt) {
        var tier = get(itemStack);

        set(itemStack, tier + amt);
    }
}
