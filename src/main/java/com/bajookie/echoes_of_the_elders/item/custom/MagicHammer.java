package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicHammer extends UpgradeHammer implements IStackPredicate {
    public MagicHammer(Settings settings) {
        super(settings);
    }

    public MagicHammer() {
        super(new FabricItemSettings().rarity(Rarity.RARE).maxCount(16));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.magic_hammer.info1"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
