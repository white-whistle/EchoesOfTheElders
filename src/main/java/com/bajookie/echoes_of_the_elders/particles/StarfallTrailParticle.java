package com.bajookie.echoes_of_the_elders.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class StarfallTrailParticle extends AnimatedParticle {
    public StarfallTrailParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration,double vx,double vy,double vz) {
        super(world, x, y, z, spriteProvider, upwardsAcceleration);
        this.setTargetColor(15916745);
        this.setVelocity(vx,vy,vz);
        this.maxAge = 30 + this.random.nextInt(12);
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public Particle scale(float scale) {
        return super.scale(0.2f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType>{
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider){
            this.spriteProvider = spriteProvider;
        }
        public Particle createParticle(DefaultParticleType particleType,ClientWorld world,
                                       double x, double y,double z,double xd, double yd ,double zd,float upwards){
            return new StarfallTrailParticle(world,x,y,z,this.spriteProvider,0f,0,0,0);
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new StarfallTrailParticle(world,x,y,z,this.spriteProvider,0f,velocityX,velocityY,velocityZ);
        }
    }
}
