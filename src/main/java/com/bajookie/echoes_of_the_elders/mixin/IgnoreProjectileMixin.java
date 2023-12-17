package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class IgnoreProjectileMixin {

    @Shadow public abstract PlayerInventory getInventory();
    @Shadow public abstract ItemCooldownManager getItemCooldownManager();
    @Shadow public abstract void startFallFlying();

    @Inject(method = "damage",at = @At(value = "INVOKE_ASSIGN" ,target = "Lnet/minecraft/entity/player/PlayerEntity;getWorld()Lnet/minecraft/world/World;",shift = At.Shift.BEFORE,ordinal = 0), cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info){
        Entity entity = source.getSource();
        EOTE.LOGGER.info("injected");
        if (entity instanceof PersistentProjectileEntity) {
            EOTE.LOGGER.info("found entity");
            if(this.getInventory().contains(ModItems.WITHER_SCALES_ITEM.getDefaultStack())){
                EOTE.LOGGER.info("found item in inventory");
                if (!this.getItemCooldownManager().isCoolingDown(ModItems.WITHER_SCALES_ITEM.asItem())) {
                    EOTE.LOGGER.info("not cool");
                    this.getItemCooldownManager().set(ModItems.WITHER_SCALES_ITEM.asItem(),20*20);
                    info.setReturnValue(false);
                    info.cancel();
                }

            }
        }

    }
}
