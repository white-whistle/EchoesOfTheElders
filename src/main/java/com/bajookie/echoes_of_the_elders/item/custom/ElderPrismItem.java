package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElderPrismItem extends BiDimensionToggleItem implements IArtifact {
    public ElderPrismItem() {
        super(new FabricItemSettings().maxCount(16).rarity(Rarity.UNCOMMON), new Pair<>(World.OVERWORLD, ModDimensions.DEFENSE_DIM_LEVEL_KEY));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (world != null) {

            var worldKey = world.getRegistryKey();

            if (worldKey == ModDimensions.DEFENSE_DIM_LEVEL_KEY) {
                tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.elder_prism.effect.to_overworld"));
            } else if (worldKey == World.OVERWORLD) {
                tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.elder_prism.effect.to_spirit_realm"));
            } else {
                tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.elder_prism.effect.other"));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean shouldDrop() {
        return false;
    }
}
