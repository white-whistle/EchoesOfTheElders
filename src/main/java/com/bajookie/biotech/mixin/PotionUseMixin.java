package com.bajookie.biotech.mixin;

import com.bajookie.biotech.BioTech;
import com.bajookie.biotech.item.ModItems;
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

@Mixin(ThrowablePotionItem.class)
public class PotionUseMixin {
    @Inject(method = "use", at = @At(value = "INVOKE",target = "Lnet/minecraft/item/ItemStack;decrement(I)V",shift = At.Shift.BEFORE), cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> info) {
        if (user.getInventory().contains(ModItems.POTION_MIRAGE.getDefaultStack())){
            int slot = user.getInventory().getSlotWithStack(ModItems.POTION_MIRAGE.getDefaultStack());
            if (!user.getItemCooldownManager().isCoolingDown(user.getInventory().getStack(slot).getItem())){
                user.getItemCooldownManager().set(user.getInventory().getStack(slot).getItem(),20*5);
                info.setReturnValue(TypedActionResult.success(user.getStackInHand(hand)));
                info.cancel();
            }
        }
    }

}
