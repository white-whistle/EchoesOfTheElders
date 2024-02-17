package com.bajookie.echoes_of_the_elders.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public interface IUpgradeItem {

    enum ClickResult {
        // upgrade successful
        SUCCESS,
        // failed to upgrade
        FAILURE,
        // forward to next handler
        FORWARD,
        // no click interaction
        PASS,
    }

    void onUpgrade(PlayerEntity user, ItemStack self, ItemStack other, StackReference cursor);

    ClickResult canUpgrade(PlayerEntity user, ItemStack self, ItemStack other);

    static ClickResult handleClick(ItemStack cursorStack, ItemStack other, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        var item = cursorStack.getItem();

        if (item instanceof IUpgradeItem iUpgradeItem) {

            if (clickType != ClickType.RIGHT) {
                return (ClickResult.PASS);
            }

            var canUpgrade = iUpgradeItem.canUpgrade(player, cursorStack, other);

            if (canUpgrade == ClickResult.SUCCESS) {
                iUpgradeItem.onUpgrade(player, cursorStack, other, cursorStackReference);
            }

            return canUpgrade;
        }

        return ClickResult.PASS;
    }
}
