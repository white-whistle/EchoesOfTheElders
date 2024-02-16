package com.bajookie.echoes_of_the_elders.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import java.util.Optional;

public interface IUpgradeItem {

    void onUpgrade(PlayerEntity user, ItemStack self, ItemStack other, StackReference cursor);

    boolean canUpgrade(PlayerEntity user, ItemStack self, ItemStack other);

    static Optional<Boolean> handleClick(ItemStack cursorStack, ItemStack other, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        var item = cursorStack.getItem();

        if (item instanceof IUpgradeItem iUpgradeItem) {

            if (clickType != ClickType.RIGHT) {
                return Optional.of(false);
            }

            if (other.isEmpty()) {
                return Optional.of(false);
            }

            if (iUpgradeItem.canUpgrade(player, cursorStack, other)) {
                iUpgradeItem.onUpgrade(player, cursorStack, other, cursorStackReference);
            }

            return Optional.of(true);
        }

        return Optional.empty();
    }
}
