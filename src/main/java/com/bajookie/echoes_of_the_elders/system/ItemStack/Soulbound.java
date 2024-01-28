package com.bajookie.echoes_of_the_elders.system.ItemStack;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class Soulbound {
    private static class Keys {
        public static final String SOULBOUND = ModIdentifier.string("soulbound");
        // out of sync and really bootleg but cant be bothered to sync this across clients
        public static final String SOULBOUND_PLAYER_NAME = ModIdentifier.string("soulbound_player_name");
    }

    public static UUID getUuid(ItemStack itemStack) {
        var nbt = itemStack.getNbt();
        if (nbt == null) return null;
        if (!nbt.contains(Keys.SOULBOUND)) return null;

        return nbt.getUuid(Keys.SOULBOUND);
    }

    public static void set(ItemStack itemStack, PlayerEntity player) {
        var nbt = itemStack.getOrCreateNbt();

        nbt.putUuid(Keys.SOULBOUND, player.getUuid());
        nbt.putString(Keys.SOULBOUND_PLAYER_NAME, player.getName().getString());
    }

    public static void setUuid(ItemStack itemStack, UUID owner) {
        var nbt = itemStack.getOrCreateNbt();

        nbt.putUuid(Keys.SOULBOUND, owner);
    }

    public static void setName(ItemStack itemStack, String name) {
        var nbt = itemStack.getOrCreateNbt();

        nbt.putString(Keys.SOULBOUND_PLAYER_NAME, name);
    }

    public static String getName(ItemStack itemStack) {
        var nbt = itemStack.getNbt();
        if (nbt == null) return null;
        if (!nbt.contains(Keys.SOULBOUND_PLAYER_NAME)) return null;

        return nbt.getString(Keys.SOULBOUND_PLAYER_NAME);
    }

    public static boolean is(ItemStack itemStack) {
        return getUuid(itemStack) != null;
    }
}
