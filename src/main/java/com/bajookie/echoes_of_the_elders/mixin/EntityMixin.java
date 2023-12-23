package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "setSneaking", at = @At("HEAD"), cancellable = true)
    public void handleSneakingStart(boolean sneaking, CallbackInfo ci) {
        if (!sneaking) return;

        var entity = (Entity) (Object) this;


        if (entity instanceof PlayerEntity player) {
            var stack = player.getInventory().getArmorStack(EquipmentSlot.FEET.getEntitySlotId());

            if (stack.isOf(ModItems.GUNHEELS)) {
                ModItems.GUNHEELS.doBulletjump(player, stack);

                ci.cancel();
            }
        }
    }

}
