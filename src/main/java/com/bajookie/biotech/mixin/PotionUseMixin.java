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
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> info) {
        BioTech.LOGGER.info("Injected");
        if (user.getInventory().contains(ModItems.POTION_MIRAGE.getDefaultStack())){
            ItemStack itemStack = user.getStackInHand(hand);
            if (!world.isClient) {
                PotionEntity potionEntity = new PotionEntity(world, user);
                potionEntity.setItem(itemStack);
                potionEntity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0f, 0.5f, 1.0f);
                world.spawnEntity(potionEntity);
            }
            int slot = user.getInventory().getSlotWithStack(ModItems.POTION_MIRAGE.getDefaultStack());
            if (!user.getItemCooldownManager().isCoolingDown(user.getInventory().getStack(slot).getItem())){
                user.getItemCooldownManager().set(user.getInventory().getStack(slot).getItem(),20*5);
                info.setReturnValue(TypedActionResult.success(user.getStackInHand(hand)));

            }
        }

    }

}
