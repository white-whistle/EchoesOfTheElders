package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.client.tooltip.ItemTooltipData;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
