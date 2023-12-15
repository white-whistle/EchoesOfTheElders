package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ICooldownReduction;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
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

public class QuickeningBand extends Item implements IArtifact, ICooldownReduction, IHasCooldown {

    protected static final int EFFECT_DURATION = 20 * 15;

    public QuickeningBand() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        if (user.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(stack);

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, EFFECT_DURATION, 1));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, EFFECT_DURATION, 1));

        user.getItemCooldownManager().set(this, this.getCooldown());

        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.quickening_band.effect"));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public float getCooldownReductionPercentage(ItemStack stack) {
        return 0.8f;
    }

    @Override
    public String cooldownInstanceId(ItemStack stack) {
        return "quickening_band";
    }

    @Override
    public int getCooldown() {
        return 20 * 60;
    }
}
