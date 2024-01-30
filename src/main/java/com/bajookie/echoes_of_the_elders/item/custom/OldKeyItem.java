package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.client.tooltip.ItemTooltipData;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Soulbound;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

        var sb = Soulbound.getUuid(stack);
        if (sb != null && player != null && !sb.equals(player.getUuid())) {
            if (player.getWorld().isClient) {
                player.sendMessage(TextUtil.translatable("message.echoes_of_the_elders.soulbound.wrong_user", new TextArgs().put("player", Soulbound.getName(stack))), true);
            }
            return ActionResult.FAIL;
        }

        if (block == ModBlocks.ARTIFACT_VAULT) {
            if (player != null && !player.getAbilities().creativeMode) {
                context.getStack().decrement(1);
            }
            context.getWorld().breakBlock(context.getBlockPos(), false);

            Block.dropStack(world, pos, this.getRandomRelicDropStack());
            context.getWorld().playSound(context.getBlockPos().getX(), context.getBlockPos().getY(), context.getBlockPos().getZ(), SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.AMBIENT, 4, 4, true);
        }
        return ActionResult.success(context.getWorld().isClient);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        var sb = Soulbound.getUuid(stack);
        if (sb != null && !sb.equals(user.getUuid())) {
            if (user.getWorld().isClient) {
                user.sendMessage(TextUtil.translatable("message.echoes_of_the_elders.soulbound.wrong_user", new TextArgs().put("player", Soulbound.getName(stack))));
            }
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

    private ItemStack getRandomRelicDropStack() {
        Random r = new Random();
        var artifacts = ModItems.registeredModItems.stream().filter(item -> item instanceof IArtifact iArtifact && iArtifact.shouldDrop()).toList();

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
