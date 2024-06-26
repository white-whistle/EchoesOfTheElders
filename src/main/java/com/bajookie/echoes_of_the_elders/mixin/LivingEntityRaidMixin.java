package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.Raid.RaidEnemyCapability;
import com.bajookie.echoes_of_the_elders.system.Raid.RaidObjectiveCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;

        ModCapabilities.RAID_OBJECTIVE.use(entity, RaidObjectiveCapability::tick);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamageRaidEnemy(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOf(DamageTypes.IN_WALL)) {
            cir.setReturnValue(false);
        }

    }
}
