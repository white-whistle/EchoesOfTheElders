package com.bajookie.biotech.mixin;

import com.bajookie.biotech.BioTech;
import com.bajookie.biotech.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionUseMixin {
    @Inject(method = "finishUsing", at = @At(value = "INVOKE",target = "Lnet/minecraft/item/ItemStack;decrement(I)V",shift = At.Shift.BEFORE), cancellable = true)
    public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> info) {
        if (user instanceof PlayerEntity player){
            if (player.getInventory().contains(ModItems.POTION_MIRAGE.getDefaultStack())){
                int slot = player.getInventory().getSlotWithStack(ModItems.POTION_MIRAGE.getDefaultStack());
                if (!player.getItemCooldownManager().isCoolingDown(player.getInventory().getStack(slot).getItem())){
                    player.getItemCooldownManager().set(player.getInventory().getStack(slot).getItem(),20*5);
                    info.setReturnValue(stack);
                    info.cancel();
                }
            }
        }
    }

}
