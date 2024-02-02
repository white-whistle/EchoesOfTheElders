package com.bajookie.echoes_of_the_elders.item.custom;


import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;

public interface IArtifact {
    default int getArtifactMaxStack() {
        return 16;
    }

    default boolean shouldDrop() {
        return true;
    }

    default boolean canArtifactMerge() {
        return true;
    }

    default boolean onArtifactClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType != ClickType.RIGHT) return false;
        if (!canArtifactMerge()) return false;


        if (stack.isOf(otherStack.getItem())) {
            var mainStack = StackLevel.get(stack) > StackLevel.get(otherStack) ? stack : otherStack;
            var secondaryStack = mainStack == stack ? otherStack : stack;

            if (!StackLevel.isMaxed(mainStack)) {
                StackLevel.raise(mainStack, 1);
                StackLevel.decrement(secondaryStack, 1);
                player.playSound(SoundEvents.BLOCK_SMITHING_TABLE_USE, 0.8f, 0.8f + player.getWorld().getRandom().nextFloat() * 0.4f);

                return true;
            }
        }

        return false;
    }
}
