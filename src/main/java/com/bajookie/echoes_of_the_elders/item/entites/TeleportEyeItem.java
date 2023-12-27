package com.bajookie.echoes_of_the_elders.item.entites;

import com.bajookie.echoes_of_the_elders.entity.custom.ChainLightningProjectileEntity;
import com.bajookie.echoes_of_the_elders.entity.custom.TeleportEyeProjectileEntity;
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

public class TeleportEyeItem extends Item {
    public TeleportEyeItem() {
        super(new FabricItemSettings().maxCount(16));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)){
            user.getItemCooldownManager().set(this,20*5);
            ItemStack itemStack = user.getStackInHand(hand);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            if (!world.isClient) {
                TeleportEyeProjectileEntity teleportEyeProjectileEntity = new TeleportEyeProjectileEntity(world, user);
                teleportEyeProjectileEntity.setItem(itemStack);
                teleportEyeProjectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 0.0f, 1.0f);
                world.spawnEntity(teleportEyeProjectileEntity);
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
        }
        return super.use(world, user, hand);
    }
}
