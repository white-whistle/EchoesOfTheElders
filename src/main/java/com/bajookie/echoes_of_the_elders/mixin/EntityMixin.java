package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.Cowplate;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "setSneaking", at = @At("HEAD"), cancellable = true)
    private void handleSneakingStart(boolean sneaking, CallbackInfo ci) {
        if (!sneaking) return;

        var entity = (Entity) (Object) this;


        if (entity instanceof PlayerEntity player) {
            var stack = player.getInventory().getArmorStack(EquipmentSlot.FEET.getEntitySlotId());

            if (stack.isOf(ModItems.GUNHEELS)) {
                if (ModItems.GUNHEELS.doBulletjump(player, stack)) {
                    ci.cancel();
                }

            }
        }
    }

    @Inject(method = "onStartedTrackingBy", at = @At("HEAD"))
    private void onStartedTrackingBy(ServerPlayerEntity player, CallbackInfo ci) {
        var self = (Entity) (Object) this;
        if (self instanceof LivingEntity livingEntity) {
            ModCapabilities.RAID_OBJECTIVE.use(livingEntity, (o) -> {
                if (o.active) {
                    o.addRaidBars(player);
                }
            });
        }
    }

    @Inject(method = "onStoppedTrackingBy", at = @At("HEAD"))
    private void onStoppedTrackingBy(ServerPlayerEntity player, CallbackInfo ci) {
        var self = (Entity) (Object) this;
        if (self instanceof LivingEntity livingEntity) {
            ModCapabilities.RAID_OBJECTIVE.use(livingEntity, (o) -> {
                if (o.active) {
                    o.removeRaidBars(player);
                }
            });
        }
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        var self = (Entity) (Object) this;
        if (self instanceof PlayerEntity target) {
            var chestStack = target.getEquippedStack(EquipmentSlot.CHEST);
            if (chestStack.getItem() instanceof Cowplate cowplate) {
                if (cowplate.handleBucket(chestStack, target, player, hand)) {
                    cir.setReturnValue(ActionResult.success(target.getWorld().isClient));
                }
            }
        }
    }

}
