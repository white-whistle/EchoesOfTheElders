package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasToggledEffect;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SteppingStone extends Item implements IArtifact, IStackPredicate, IHasToggledEffect, IRaidReward {
    public static StackedItemStat.Float BONUS_STEP = new StackedItemStat.Float(1f, 8f);

    public SteppingStone() {
        super(new ArtifactItemSettings());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.join(
                TextUtil.translatable("ability.echoes_of_the_elders.step_boost.name"),
                IHasToggledEffect.getText(stack)
        ));
        tooltip.add(TextUtil.translatable("ability.echoes_of_the_elders.step_boost.info1", new TextArgs().putF("step_bonus", BONUS_STEP.get(stack))));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
