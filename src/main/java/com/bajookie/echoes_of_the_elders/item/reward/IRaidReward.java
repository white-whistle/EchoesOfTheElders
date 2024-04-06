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

    static ItemStack getRaidReward(RaidRewardDropContext ctx) {
        var possibleRewards = ModItems.registeredModItems.stream().filter(item -> DropCondition.canDrop(item, ctx)).toList();

        return possibleRewards.get(r.nextInt(possibleRewards.size())).getDefaultStack();
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
    }
}
