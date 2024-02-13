package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.effects.IRemoveEffect;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

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
    @Inject(method = "shouldDropXp",at = @At("HEAD"),cancellable = true)
    private void shouldDropXP(CallbackInfoReturnable<Boolean> cir) {
        var self = (LivingEntity) (Object) this;
        if (ModCapabilities.RAID_ENEMY.hasCapability(self)) {
            cir.setReturnValue(false);
        }
    }

}
