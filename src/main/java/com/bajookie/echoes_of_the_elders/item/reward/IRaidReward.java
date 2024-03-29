package com.bajookie.echoes_of_the_elders.item.reward;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Raid.RaidObjectiveCapability;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public interface IRaidReward {

    Random r = new Random();
    List<DropCondition> ALWAYS_DROP = List.of();

    static ItemStack getRaidReward(RaidRewardDropContext ctx) {
        var possibleRewards = ModItems.registeredModItems.stream().filter(item -> item instanceof IRaidReward iRaidReward && iRaidReward.shouldDrop(ctx)).toList();

        return possibleRewards.get(r.nextInt(possibleRewards.size())).getDefaultStack();
    }

    default boolean shouldDrop(RaidRewardDropContext ctx) {
        return getDropConditions().stream().allMatch(d -> d.shouldDrop(ctx));
    }

    default List<DropCondition> getDropConditions() {
        return ALWAYS_DROP;
    }

    default ArtifactRarity getRarity() {
        return ArtifactRarity.COMMON;
    }

    enum ArtifactRarity {
        COMMON,
        UNCOMMON,
        RARE,
        EPIC,
        MYTHICAL,
        LEGENDARY,

        CREATIVE,
    }

    record RaidRewardDropContext(World world, RaidObjectiveCapability raidObjectiveCapability, PlayerEntity player,
                                 int level) {

        boolean levelBetween(int min, int max) {
            return this.level >= min && this.level < max;
        }
    }

    abstract class DropCondition {
        abstract boolean shouldDrop(RaidRewardDropContext ctx);

        void appendTooltip(List<Text> tooltip) {
        }

        static class Level extends DropCondition {
            public int min, max;

            public Level(int min, int max) {
                this.min = min;
                this.max = max;
            }

            @Override
            boolean shouldDrop(RaidRewardDropContext ctx) {
                return ctx.level >= min && ctx.level < this.max;
            }

            @Override
            void appendTooltip(List<Text> tooltip) {
                tooltip.add(TextUtil.translatable("raid_drops.echoes_of_the_elders.level", new TextArgs().putI("min", min).putI("max", max)));
            }
        }
    }

}
