package com.bajookie.echoes_of_the_elders.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PotionMirageItem extends Item {
    public PotionMirageItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.potion_mirage.effect"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
