package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public class HostileEntityMixin {
    
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
