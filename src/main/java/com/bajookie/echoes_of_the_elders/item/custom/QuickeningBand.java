package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ICooldownReduction;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuickeningBand extends Item implements IArtifact, ICooldownReduction, IHasCooldown, IStackPredicate {

    protected static final int EFFECT_DURATION = 20 * 15;
    protected StackedItemStat.Int effectLevel = new StackedItemStat.Int(1, 8);

    public QuickeningBand() {
        super(new FabricItemSettings().maxCount(16).rarity(Rarity.EPIC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        if (user.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(stack);

        var eLevel = this.effectLevel.get(stack);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, EFFECT_DURATION, eLevel));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, EFFECT_DURATION, eLevel));

        user.getItemCooldownManager().set(this, this.getCooldown(stack));

        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        var eLevel = this.effectLevel.get(stack) + 1;

        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.quickening_band.effect", new TextArgs().putI("level", eLevel)));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public float getCooldownReductionPercentage(ItemStack stack) {
        return 0.1f;
    }

    @Override
    public String cooldownInstanceId(ItemStack stack) {
        return "quickening_band";
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return 20 * 60;
    }
}
