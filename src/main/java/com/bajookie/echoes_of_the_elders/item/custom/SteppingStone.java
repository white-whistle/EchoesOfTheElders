package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasToggledEffect;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(max = 10)
public class SteppingStone extends Item implements IArtifact, IStackPredicate, IHasToggledEffect, IRaidReward {
    public static StackedItemStat.Float BONUS_STEP = new StackedItemStat.Float(1f, 8f);

    public SteppingStone() {
        super(new ArtifactItemSettings());
    }

    public static final Ability MOUNTAIN_SCALER_ABILITY = new Ability("mountain_scaler", Ability.AbilityType.PASSIVE, Ability.AbilityTrigger.TOGGLED) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putF("step_bonus", BONUS_STEP.get(stack)));
        }
    };

    public static final List<Ability> ABILITIES = List.of(MOUNTAIN_SCALER_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }
}
