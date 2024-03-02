package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(method = "isAffectedByDaylight", at = @At("HEAD"), cancellable = true)
    private void disableDaylightBurnInSpiritRealm(CallbackInfoReturnable<Boolean> cir) {
        var mob = (MobEntity) (Object) this;
        var world = mob.getWorld();
        if (world.getRegistryKey() == ModDimensions.DEFENSE_DIM_LEVEL_KEY) {
            cir.setReturnValue(false);
        }
    }

}
