package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.client.animation.AnimationUtil;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VitalityPumpItem extends Item implements IArtifact, IHasCooldown {

    protected float healAmt;

    public VitalityPumpItem(float healAmt) {
        super(new FabricItemSettings().maxCount(1));

        this.healAmt = healAmt;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(itemStack);

        this.activate(world, user);

        user.incrementStat(Stats.USED.getOrCreateStat(this));

        return TypedActionResult.success(itemStack, world.isClient());
    }

    public void activate(World world, PlayerEntity user) {
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_HONEY_BLOCK_PLACE, SoundCategory.PLAYERS, 5f, 0.2f);

        user.getItemCooldownManager().set(this, this.getCooldown());

        if (!world.isClient) {
            user.heal(this.healAmt);
        } else {
            AnimationUtil.VITALITY_PUMP_HEARTBEAT_ANIMATION.start();
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.vitality_pump.effect"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getCooldown() {
        return 60;
    }
}
