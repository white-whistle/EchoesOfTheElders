package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.effects.IRemoveEffect;
import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.HareleapStriders;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow
    private float movementSpeed;

    @Inject(method = "onStatusEffectRemoved", at = @At("TAIL"))
    private void onEffectRemoved(StatusEffectInstance effect, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        var world = entity.getWorld();

        if (!world.isClient()) {
            if (effect.getEffectType() instanceof IRemoveEffect iRemoveEffect) {
                iRemoveEffect.onRemoved(effect, entity);
            }
        }
    }

    @Inject(method = "shouldDropLoot", at = @At("HEAD"), cancellable = true)
    private void shouldDropLoot(CallbackInfoReturnable<Boolean> cir) {
        var self = (LivingEntity) (Object) this;
        if (ModCapabilities.RAID_ENEMY.hasCapability(self)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "shouldDropXp", at = @At("HEAD"), cancellable = true)
    private void shouldDropXP(CallbackInfoReturnable<Boolean> cir) {
        var self = (LivingEntity) (Object) this;
        if (ModCapabilities.RAID_ENEMY.hasCapability(self)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getMovementSpeed()F", at = @At("HEAD"), cancellable = true)
    private void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        var self = (LivingEntity) (Object) this;
        var legsStack = self.getEquippedStack(EquipmentSlot.LEGS);
        if (legsStack.isOf(ModItems.ATLAS_GREAVES) && self.isOnGround()) {
            cir.setReturnValue((float) self.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
        }

        if (self.isOnGround()) return;
        var statusInstance = self.getStatusEffect(ModEffects.HOP);
        if (statusInstance != null) {
            var increase = (statusInstance.getAmplifier() * 0.1) + 1;
            cir.setReturnValue((float) (movementSpeed * increase));
        }
    }

    @Inject(method = "getMovementSpeed(F)F", at = @At("HEAD"), cancellable = true)
    private void getMovementSpeedSlippery(CallbackInfoReturnable<Float> cir) {
        var self = (LivingEntity) (Object) this;
        var legsStack = self.getEquippedStack(EquipmentSlot.LEGS);
        if (legsStack.isOf(ModItems.ATLAS_GREAVES) && self.isOnGround()) {
            cir.setReturnValue((float) self.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
        }

        if (self.isOnGround()) return;
        var statusInstance = self.getStatusEffect(ModEffects.HOP);
        if (statusInstance != null) {
            var increase = (statusInstance.getAmplifier() * 0.1) + 1;
            cir.setReturnValue((float) (movementSpeed * increase));
        }
    }

    @Inject(method = "jump", at = @At("HEAD"))
    private void jump(CallbackInfo ci) {
        var self = (LivingEntity) (Object) this;
        var feetStack = self.getEquippedStack(EquipmentSlot.FEET);
        if (feetStack.getItem() instanceof HareleapStriders hareleapStriders) {
            hareleapStriders.handleJump(self, feetStack);
        }
    }

}
