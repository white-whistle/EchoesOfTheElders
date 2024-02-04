package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.Raid.RaidEnemyCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityRaidMixin {

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;

        // on raid enemy death
        ModCapabilities.RAID_ENEMY.use(entity, RaidEnemyCapability::onDeath);

        // on raid objective death
        ModCapabilities.RAID_OBJECTIVE.use(entity, o -> {
            if (!o.active) return;
            o.onLose();
        });
    }

}
