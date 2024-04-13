package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(max = 50)
public class MagicHammer extends UpgradeHammer implements IStackPredicate, IRaidReward, IArtifact {
    public MagicHammer(Settings settings) {
        super(settings);
    }

    public MagicHammer() {
        super(new FabricItemSettings().rarity(net.minecraft.util.Rarity.RARE).maxCount(16));
    }

    @Override
    public int getArtifactMaxStack() {
        return 1;
    }

    public boolean isOneTimeUse() {
        return true;
    }

    public static final Ability ARTISANS_TOUCH_ABILITY = new Ability("artisans_touch", Ability.AbilityType.SPECIAL, Ability.AbilityTrigger.CLICK_STACK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
            if (stack.getItem() instanceof MagicHammer magicHammer && magicHammer.isOneTimeUse()) {
                section.line("one_time_use");
            }
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(ARTISANS_TOUCH_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public Model getBaseModel() {
        return Models.HANDHELD;
    }
}
