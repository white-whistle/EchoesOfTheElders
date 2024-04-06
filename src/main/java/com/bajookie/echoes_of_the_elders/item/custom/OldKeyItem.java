package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.client.tooltip.ItemTooltipData;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Soulbound;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Tier;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class OldKeyItem extends Item {
    public OldKeyItem() {
        super(new FabricItemSettings().maxCount(4).rarity(Rarity.UNCOMMON));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var pos = context.getBlockPos();
        var world = context.getWorld();

        Block block = context.getWorld().getBlockState(pos).getBlock();
        PlayerEntity player = context.getPlayer();
        var stack = context.getStack();

        if (player == null) return ActionResult.FAIL;
        if (Soulbound.notOwner(stack, player)) {
            return ActionResult.FAIL;
        }

        if (block == ModBlocks.ARTIFACT_VAULT) {
            var tier = Tier.get(stack);

            if (!player.getAbilities().creativeMode) {
                context.getStack().decrement(1);
            }

            context.getWorld().breakBlock(context.getBlockPos(), false);

            var reward = this.getRewardStack(tier);
            Soulbound.set(reward, player);

            Block.dropStack(world, pos, reward);

            context.getWorld().playSound(context.getBlockPos().getX(), context.getBlockPos().getY(), context.getBlockPos().getZ(), SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.AMBIENT, 4, 4, true);
        }
        return ActionResult.success(context.getWorld().isClient);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (Soulbound.notOwner(stack, user)) {
            return ActionResult.FAIL;
        }

        var isRaidObjective = ModCapabilities.RAID_OBJECTIVE.use(entity, o -> {
            var wasAdded = o.tryAddKey(stack.copyWithCount(1), user);
            if (!wasAdded) return Optional.of(false);

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }

            return Optional.of(true);
        });

        if (isRaidObjective.isPresent() && isRaidObjective.get()) return ActionResult.SUCCESS;

        return super.useOnEntity(stack, user, entity, hand);
    }

    private ItemStack getRewardStack(int tier) {
        var invSize = 3 * 9;
        var items = Stream.generate(this::getRandomRelicDropStack).limit(tier + 1).toList();
        var bagChunks = Lists.newArrayList(Iterables.partition(items, invSize - 1));
        Collections.reverse(bagChunks);

        // XXXc
        // X X X c
        // c X X X
        // fill c
        // set inv c
        // prev: c
        // fill X
        // add prev c
        // set inv X

        ItemStack prev = null;

        for (var bagChunk : bagChunks) {
            var bag = new ItemStack(ModItems.PANDORAS_BAG);
            var inv = new SimpleInventory(invSize);
            var slot = 0;
            for (var stack : bagChunk) {
                inv.setStack(slot++, stack);
            }

            if (prev != null) {
                inv.setStack(slot, prev);
            }

            PandorasBag.setBagInventory(bag, inv);

            prev = bag;
        }

        return prev;
    }

    private ItemStack getRandomRelicDropStack() {
        Random r = new Random();
        var ctx = new IRaidReward.RaidRewardDropContext(null, null, null, 0);
        var artifacts = ModItems.registeredModItems.stream().filter(item -> DropCondition.canDrop(item, ctx)).toList();

        var randomArtifactItem = artifacts.get(r.nextInt(artifacts.size()));

        return new ItemStack(randomArtifactItem, 1);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.old_key_item.effect"));
        tooltip.add(TextUtil.component(new ItemTooltipData(new ItemStack(ModBlocks.ARTIFACT_VAULT))));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
