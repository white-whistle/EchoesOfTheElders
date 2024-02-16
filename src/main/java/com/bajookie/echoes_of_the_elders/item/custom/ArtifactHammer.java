package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

public class ArtifactHammer extends MagicHammer implements IArtifact, IHasCooldown {
    StackedItemStat.Int cooldown = new StackedItemStat.Int(20 * (60 * 60) * 3, 20 * (60 * 30));

    public ArtifactHammer() {
        super(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1));
    }

    @Override
    public boolean canUpgrade(PlayerEntity user, ItemStack self, ItemStack other) {
        if (user.getItemCooldownManager().isCoolingDown(self.getItem())) return false;

        return super.canUpgrade(user, self, other);
    }

    @Override
    public void onUpgrade(PlayerEntity user, ItemStack self, ItemStack other, StackReference cursor) {
        super.onUpgrade(user, self, other, cursor);

        user.getItemCooldownManager().set(self.getItem(), getCooldown(self));
    }

    @Override
    public int getArtifactMaxStack() {
        return 128;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return cooldown.get(itemStack);
    }
}
