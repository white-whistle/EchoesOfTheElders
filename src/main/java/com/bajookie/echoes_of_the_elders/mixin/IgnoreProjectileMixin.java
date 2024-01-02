package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.util.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class IgnoreProjectileMixin {

    @Shadow
    public abstract PlayerInventory getInventory();

    @Shadow
    public abstract ItemCooldownManager getItemCooldownManager();

    @Shadow
    public abstract void startFallFlying();

    @Inject(method = "damage", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;getWorld()Lnet/minecraft/world/World;", shift = At.Shift.BEFORE, ordinal = 0), cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        Entity entity = source.getSource();
        if (entity instanceof PersistentProjectileEntity) {
            PlayerInventory inventory = this.getInventory();

            var stack = InventoryUtil.find(inventory, ModItems.WITHER_SCALES_ITEM);
            if (stack == null) return;

            if (!this.getItemCooldownManager().isCoolingDown(ModItems.WITHER_SCALES_ITEM)) {
                this.getItemCooldownManager().set(ModItems.WITHER_SCALES_ITEM, ModItems.WITHER_SCALES_ITEM.getCooldown(stack));
                info.setReturnValue(false);
                info.cancel();
            }

        }

    }
}
