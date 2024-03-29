package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.ItemStack.RaidReward;
import com.bajookie.echoes_of_the_elders.util.Interator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;

public class RaidDebugItem extends Item implements IArtifact {
    public RaidDebugItem() {
        super(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        ModCapabilities.RAID_OBJECTIVE.use(entity, o -> {
            o.cleanupEnemies();
            o.spawnWave();
        });

        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var pos = context.getBlockPos();
        var world = context.getWorld();
        var player = context.getPlayer();
        var hand = context.getHand();

        if (player == null) return ActionResult.FAIL;

        var stack = player.getStackInHand(hand);
        var blockState = world.getBlockState(pos);

        if (blockState.isOf(ModBlocks.TOTEM_SPAWN_BLOCK)) {
            // var level = StackLevel.get(stack);
            var raidRewards = ModItems.registeredModItems.stream().filter(item -> item instanceof IRaidReward).toList();
            var inv = new SimpleInventory(raidRewards.size());
            new Interator(raidRewards.size()).forEach(i -> {
                inv.setStack(i, raidRewards.get(i).getDefaultStack());
            });

            var bag = new ItemStack(ModItems.PANDORAS_BAG);
            RaidReward.set(bag, inv);

            player.giveItemStack(bag);
        }

        return ActionResult.SUCCESS;
    }

}
