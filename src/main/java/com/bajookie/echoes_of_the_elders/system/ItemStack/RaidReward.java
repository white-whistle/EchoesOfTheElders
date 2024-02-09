package com.bajookie.echoes_of_the_elders.system.ItemStack;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;

public class RaidReward {
    private static class Keys {
        public static final String REWARD_INV = ModIdentifier.string("reward_inv");
        public static final String REWARD_INV_SIZE = ModIdentifier.string("reward_inv_size");
    }

    public static SimpleInventory get(ItemStack itemStack) {
        var nbt = itemStack.getNbt();
        if (nbt == null) return new SimpleInventory(0);

        var size = nbt.getInt(Keys.REWARD_INV_SIZE);
        var inv = new SimpleInventory(size);
        inv.readNbtList(nbt.getList(Keys.REWARD_INV, NbtElement.COMPOUND_TYPE));

        return inv;
    }

    public static ItemStack set(ItemStack itemStack, SimpleInventory simpleInventory) {
        var nbt = itemStack.getOrCreateNbt();

        nbt.putInt(Keys.REWARD_INV_SIZE, simpleInventory.size());
        nbt.put(Keys.REWARD_INV, simpleInventory.toNbtList());

        return itemStack;
    }

    public static SimpleInventory addItem(ItemStack itemStack, ItemStack item) {
        var inv = get(itemStack);

        var size = inv.size();
        var newInv = new SimpleInventory(size + 1);
        for (int i = 0; i < size; i++) {
            newInv.setStack(i, inv.getStack(i));
        }
        newInv.setStack(size, item);
        set(itemStack, newInv);

        return newInv;
    }

    public static SimpleInventory queueItem(ItemStack itemStack, ItemStack item) {
        var inv = get(itemStack);

        var size = inv.size();
        var newInv = new SimpleInventory(size + 1);

        newInv.setStack(0, item);

        for (int i = 1; i < size + 1; i++) {
            newInv.setStack(i, inv.getStack(i - 1));
        }
        set(itemStack, newInv);

        return newInv;
    }

    public static ItemStack dequeueItem(ItemStack itemStack) {
        var inv = get(itemStack);
        var size = inv.size();
        if (size == 0) return null;

        var stack = inv.getStack(0);
        var newInv = new SimpleInventory(size - 1);

        for (int i = 0; i < size - 1; i++) {
            newInv.setStack(i, inv.getStack(i + 1));
        }

        set(itemStack, newInv);

        return stack;
    }

    public boolean hasInventory(ItemStack itemStack) {
        var nbt = itemStack.getNbt();
        if (nbt == null) return false;

        return nbt.getInt(Keys.REWARD_INV_SIZE) > 0;
    }
}
