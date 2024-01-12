package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.mixin.ItemCooldownManagerAccessor;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TimeTokenItem extends Item implements IArtifact, IHasCooldown {
    protected final StackedItemStat.Int cooldown = new StackedItemStat.Int(1200 * 20, 60 * 20);

    public TimeTokenItem() {
        super(new FabricItemSettings().maxCount(16));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        var cdm = user.getItemCooldownManager();

        if (!cdm.isCoolingDown(this)) {
            clearCooldowns(cdm);

            cdm.set(this, this.getCooldown(stack));
        }

        return TypedActionResult.pass(stack);
    }

    public static void clearCooldowns(ItemCooldownManager itemCooldownManager) {
        var entries = ((ItemCooldownManagerAccessor) itemCooldownManager).getEntries();

        for (var key : entries.keySet()) {
            itemCooldownManager.remove(key);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.time_token.info"));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return this.cooldown.get(itemStack);
    }
}
