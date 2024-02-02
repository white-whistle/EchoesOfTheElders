package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class RadiantLotusItem extends Item implements IArtifact, IHasCooldown {
    protected StackedItemStat.Int cooldown = new StackedItemStat.Int(20 * 60 * 5, 60);

    public RadiantLotusItem() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            var stack = user.getStackInHand(hand);
            user.getItemCooldownManager().set(this, this.getCooldown(stack));
            Collection<StatusEffectInstance> effects = user.getStatusEffects();
            for (StatusEffectInstance effect : effects) {
                if (!effect.getEffectType().isBeneficial()) {
                    user.removeStatusEffect(effect.getEffectType());
                }
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.radiant_lotus.effect"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return cooldown.get(stack);
    }
}
