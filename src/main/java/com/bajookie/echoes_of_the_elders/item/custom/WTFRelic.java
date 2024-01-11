package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ICooldownReduction;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

public class WTFRelic extends Item implements IArtifact, ICooldownReduction {
    public WTFRelic() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public boolean shouldDrop() {
        return false;
    }

    @Override
    public float getCooldownReductionPercentage(ItemStack stack) {
        return 1;
    }

    @Override
    public String cooldownInstanceId(ItemStack stack) {
        return "WTF?";
    }
}
