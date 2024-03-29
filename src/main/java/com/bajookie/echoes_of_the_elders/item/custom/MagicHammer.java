package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicHammer extends UpgradeHammer implements IStackPredicate, IRaidReward {
    public MagicHammer(Settings settings) {
        super(settings);
    }

    public MagicHammer() {
        super(new FabricItemSettings().rarity(net.minecraft.util.Rarity.RARE).maxCount(16));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.magic_hammer.info1"));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.one_time_use"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
