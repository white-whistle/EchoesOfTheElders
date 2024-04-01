package com.bajookie.echoes_of_the_elders.item;

import net.minecraft.item.ItemStack;

public interface IProjectileProvider {
    default boolean canProvideProjectile(ItemStack self, ItemStack rangedWeapon) {
        return true;
    }

    default void onConsume(ItemStack self, ItemStack rangedWeapon) {

    }

    ItemStack getProjectile(ItemStack self, ItemStack rangedWeapon);
}
