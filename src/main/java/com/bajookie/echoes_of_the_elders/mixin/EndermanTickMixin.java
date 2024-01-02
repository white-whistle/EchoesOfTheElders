package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.entity.custom.EldermanEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndermanEntity.class)
public class EndermanTickMixin {
    @Redirect(method = "tickMovement",at = @At(value = "INVOKE" , target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
    public void addParticleProxy(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        EndermanEntity enderman = (EndermanEntity) (Object)this;
        if (enderman instanceof EldermanEntity elderman){
            if (elderman.getWorld().isClient) {
                for (int i = 0; i < 2; ++i) {
                    elderman.getWorld().addParticle(ParticleTypes.ENCHANT, elderman.getParticleX(0.5), elderman.getRandomBodyY() - 0.25, elderman.getParticleZ(0.5), (elderman.getRandom().nextDouble() - 0.5) * 0.1, -elderman.getRandom().nextDouble()*0.1, (elderman.getRandom().nextDouble() - 0.5) * 0.1);
                }
            }
        } else {
            if (enderman.getWorld().isClient) {
                for (int i = 0; i < 2; ++i) {
                    enderman.getWorld().addParticle(ParticleTypes.PORTAL, enderman.getParticleX(0.5), enderman.getRandomBodyY() - 0.25, enderman.getParticleZ(0.5), (enderman.getRandom().nextDouble() - 0.5) * 2.0, -enderman.getRandom().nextDouble(), (enderman.getRandom().nextDouble() - 0.5) * 2.0);
                }
            }
        }
    }
}
