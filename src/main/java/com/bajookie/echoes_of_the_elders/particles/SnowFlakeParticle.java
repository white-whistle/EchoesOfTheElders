package com.bajookie.echoes_of_the_elders.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.hit.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SnowFlakeParticle extends AnimatedParticle {
    public SnowFlakeParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration,double vx,double vy,double vz) {
        super(world, x, y, z, spriteProvider, upwardsAcceleration);
        Random r = new Random();
        this.setVelocity(vx+r.nextInt(-4,4)*0.05,vy,vz+r.nextInt(-4,4)*0.05);
        this.maxAge = 30 + this.random.nextInt(12);
        this.setSpriteForAge(spriteProvider);
        this.scale(2f);
    }

    @Override
    public void tick() {
        super.tick();

    }


    @Override
    public Particle scale(float scale) {
        return super.scale(2f);
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
            return new SnowFlakeParticle(world,x,y,z,this.spriteProvider,-0.1f,0,0,0);
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SnowFlakeParticle(world,x,y,z,this.spriteProvider,-0.1f,velocityX,velocityY,velocityZ);
        }
    }
}
