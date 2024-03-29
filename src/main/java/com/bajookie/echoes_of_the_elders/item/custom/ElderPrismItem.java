package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.block.custom.IPrismActionable;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElderPrismItem extends BiDimensionToggleItem implements IArtifact, IStackPredicate {
    public ElderPrismItem() {
        super(new ArtifactItemSettings(), new Pair<>(World.OVERWORLD, ModDimensions.DEFENSE_DIM_LEVEL_KEY));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (world != null) {

            var worldKey = world.getRegistryKey();

            if (worldKey == ModDimensions.DEFENSE_DIM_LEVEL_KEY) {
                tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.elder_prism.effect.to_overworld"));
            } else if (worldKey == World.OVERWORLD) {
                tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.elder_prism.effect.to_spirit_realm"));
            } else {
                tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.elder_prism.effect.other"));
            }
        }
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.elder_prism.effect.interact"));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        var blockPos = context.getBlockPos();
        var world = context.getWorld();
        var stack = context.getStack();
        var user = context.getPlayer();

        var blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof IPrismActionable iPrismActionable && user != null && !user.getItemCooldownManager().isCoolingDown(this)) {
            var side = context.getSide();
            if (iPrismActionable.onPrism(stack, user, world, blockPos, side)) {
                if (world.isClient) {
                    MinecraftClient.getInstance().gameRenderer.showFloatingItem(stack);
                }
                world.playSound(user, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.PLAYERS, 1, 0.5f);
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnBlock(context);
    }
}
