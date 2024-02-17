package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;

public class UpgradeHammer extends Item implements IUpgradeItem {
    public UpgradeHammer(Settings settings) {
        super(settings);
    }

    @Override
    public void onUpgrade(PlayerEntity user, ItemStack self, ItemStack other, StackReference cursor) {
        StackLevel.raise(other, 1);

        if (!user.getAbilities().creativeMode) {
            self.decrement(1);
        }
    }

    public ClickResult success(PlayerEntity user, ItemStack self, ItemStack other) {
        user.playSound(SoundEvents.BLOCK_SMITHING_TABLE_USE, 0.8f, 0.8f + user.getWorld().getRandom().nextFloat() * 0.4f);
        return (ClickResult.SUCCESS);
    }

    public ClickResult fail(PlayerEntity user, ItemStack self, ItemStack other) {
        user.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.2f, 0.8f + user.getWorld().getRandom().nextFloat() * 0.4f);
        return (ClickResult.FAILURE);
    }

    @Override
    public ClickResult canUpgrade(PlayerEntity user, ItemStack self, ItemStack other) {
        if (!(other.getItem() instanceof IArtifact)) return fail(user, self, other);
        if (StackLevel.isMaxed(other)) return fail(user, self, other);

        return success(user, self, other);
    }
}
