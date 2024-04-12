package com.bajookie.echoes_of_the_elders.item;

import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.ability.TooltipHelper;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Soulbound;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Tier;
import com.bajookie.echoes_of_the_elders.system.Text.ModText;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.system.Text.TooltipSection;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class ModGenericTooltip {
    public static void appendGenericTooltips(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, boolean isShifting) {
        var item = stack.getItem();

        boolean skipItemCooldown = false;
        if (item instanceof IArtifact iArtifact) {
            var abilities = iArtifact.getAbilities(stack);
            var info = iArtifact.getAdditionalInfo(stack);

            if (abilities.stream().anyMatch(Ability::hasCooldown)) skipItemCooldown = true;

            new TooltipHelper(stack, world, tooltip, context).sections(
                    Stream.concat(abilities.stream(), info.stream()).toArray(TooltipSection[]::new)
            );
        }

        AtomicBoolean padded = new AtomicBoolean(false);
        Runnable tryPad = () -> {
            if (!padded.get()) {
                padded.set(true);
                tooltip.add(Text.empty());
            }
        };


        if (item instanceof ICooldownReduction iCooldownReduction) {
            var p = iCooldownReduction.getCooldownReductionPercentage(stack);
            tryPad.run();
            tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.cooldown_reduction", new TextArgs().putI("percent", Math.round(p * 100))).styled(s -> s.withColor(Formatting.BLUE)));
        }

        if (item instanceof IArtifact iArtifact) {
            var count = StackLevel.get(stack);
            var maxCount = StackLevel.getMax(stack);
            var isSingleItem = maxCount == count && count == 1;

            if (!isSingleItem && iArtifact.canArtifactMerge()) {
                tryPad.run();
                if (StackLevel.isMaxed(stack)) {
                    tooltip.add(ModText.STACK_LEVEL.apply(TextUtil.translatable("tooltip.echoes_of_the_elders.artifact_stack.max", new TextArgs().putI("count", count).putI("maxCount", maxCount, Formatting.DARK_GRAY))));
                } else {
                    tooltip.add(ModText.STACK_LEVEL.apply(TextUtil.translatable("tooltip.echoes_of_the_elders.artifact_stack", new TextArgs().putI("count", count).putI("maxCount", maxCount, Formatting.DARK_GRAY))));
                }
            }
        }

        var tier = Tier.get(stack);
        if (tier > 0) {
            tryPad.run();
            tooltip.add((TextUtil.translatable("tooltip.echoes_of_the_elders.tier", new TextArgs().putI("tier", tier))));
        }

        if (!skipItemCooldown) {
            var cooldownMessage = IHasCooldown.getCooldownMessage(stack, world);
            if (cooldownMessage != null) {
                tooltip.add(cooldownMessage);
            }
        }

        var soulbound = Soulbound.is(stack);
        if (soulbound) {
            tryPad.run();
            var name = Soulbound.getName(stack);
            tooltip.add((TextUtil.translatable("tooltip.echoes_of_the_elders.soulbound", new TextArgs().put("player", name))));
        }

        if (isShifting) {
            DropCondition.appendTooltip(stack, world, tooltip, context);
        }
    }
}
