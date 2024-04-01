package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.entity.custom.ICustomProjectileCrit;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", ordinal = 0))
    public void spawnCritParticle(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        var self = (PersistentProjectileEntity) (Object) (this);

        if (self instanceof ICustomProjectileCrit iCustomProjectileCrit) {
            iCustomProjectileCrit.spawnCritParticle(instance, x, y, z, velocityX, velocityY, velocityZ);
            return;
        }

        instance.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
    }
}
