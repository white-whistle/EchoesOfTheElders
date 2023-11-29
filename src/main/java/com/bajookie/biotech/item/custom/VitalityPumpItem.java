package com.bajookie.biotech.item.custom;

import com.bajookie.biotech.client.animation.AnimationUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VitalityPumpItem extends Item {

    protected float healAmt;
    public VitalityPumpItem(float healAmt) {
        super(new FabricItemSettings().maxCount(1));

        this.healAmt = healAmt;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        this.activate(world, user);

        user.incrementStat(Stats.USED.getOrCreateStat(this));

        return TypedActionResult.success(itemStack, world.isClient());
    }

    public void activate(World world, PlayerEntity user) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WARDEN_HEARTBEAT, SoundCategory.NEUTRAL, 1f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

        user.getItemCooldownManager().set(this, 60);

        if (!world.isClient) {
            user.heal(this.healAmt);
        } else {
            AnimationUtil.VITALITY_PUMP_HEARTBEAT_ANIMATION.start();
        }
    }
}
