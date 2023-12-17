package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionUseMixin {
    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V", shift = At.Shift.BEFORE), cancellable = true)
    public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> info) {
        if (user instanceof PlayerEntity player) {
            var inventory = player.getInventory();
            var mirageDefaultStack = ModItems.POTION_MIRAGE.getDefaultStack();
            if (inventory.contains(mirageDefaultStack)) {
                int slot = inventory.getSlotWithStack(mirageDefaultStack);
                var mirageStack = inventory.getStack(slot);
                var cdm = player.getItemCooldownManager();
                if (!cdm.isCoolingDown(ModItems.POTION_MIRAGE)) {
                    cdm.set(ModItems.POTION_MIRAGE, ModItems.POTION_MIRAGE.getCooldown(mirageStack));
                    info.setReturnValue(stack);
                    info.cancel();
                }
            }
        }
    }

}
