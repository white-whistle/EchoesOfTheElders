package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.MagmaBullet;
import com.bajookie.echoes_of_the_elders.entity.custom.PelletProjectile;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.sound.ModSounds;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class AncientMinigun extends Item implements IArtifact {
    public AncientMinigun() {
        super(new FabricItemSettings().maxCount(1).maxDamage(200));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 600;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        world.playSound(user.getX(),user.getY(),user.getZ(), ModSounds.MINIGUN_CHARGE, SoundCategory.PLAYERS,4f,1f,false);
        return super.use(world, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        world.playSound(user.getX(),user.getY(),user.getZ(), ModSounds.MINIGUN_END, SoundCategory.PLAYERS,4f,1f,false);
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        world.playSound(user.getX(),user.getY(),user.getZ(), ModSounds.MINIGUN_END, SoundCategory.PLAYERS,4f,1f,false);
        return super.finishUsing(stack, world, user);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks < 590) {
            world.playSound(user.getX(),user.getY(),user.getZ(), ModSounds.MINIGUN_FIRE2, SoundCategory.PLAYERS,4f,1f,false);
            int currentAmmo = stack.getDamage();
            if (currentAmmo >= this.getMaxDamage() && user instanceof PlayerEntity player) {
                player.getItemCooldownManager().set(this, 20 * 7);
                player.stopUsingItem();
            } else {
                if (!world.isClient) {
                    if (user instanceof PlayerEntity player && !player.getItemCooldownManager().isCoolingDown(this)){
                        PelletProjectile bullet = new PelletProjectile(world, user.getEyePos().x, user.getEyePos().y-0.2, user.getEyePos().z, 6, user, user.getPitch(), user.getYaw());
                        bullet.setVelocity(user, user.getPitch(), user.getYaw(), user.getRoll(), 5f, 7);
                        world.spawnEntity(bullet);
                    }
                }
                stack.setDamage(currentAmmo + 1);
            }
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            if ((selected & !player.isUsingItem()) || !selected) {
                if (stack.getDamage() != 0) {
                    stack.setDamage(stack.getDamage() - 1);
                    if (stack.getDamage() != 0) {
                        stack.setDamage(stack.getDamage() - 1);
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
