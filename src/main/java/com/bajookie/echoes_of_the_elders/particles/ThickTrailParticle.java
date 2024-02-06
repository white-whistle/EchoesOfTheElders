package com.bajookie.echoes_of_the_elders.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class ThickTrailParticle extends AnimatedParticle {
    protected ThickTrailParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration,double vx,double vy,double vz) {
        super(world, x, y, z, spriteProvider, upwardsAcceleration);
        this.setTargetColor(15916745);
        this.maxAge = 80;
        this.setVelocity(0,0,0);
        this.scale(1.5f);
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }
        this.velocityY -= 0.04 * (double)this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        if (this.ascending && this.y == this.prevPosY) {
            this.velocityX *= 1.1;
            this.velocityZ *= 1.1;
        }
        this.velocityX *= (double)this.velocityMultiplier;
        this.velocityY *= (double)this.velocityMultiplier;
        this.velocityZ *= (double)this.velocityMultiplier;
        if (this.onGround) {
            this.velocityX *= (double)0.7f;
            this.velocityZ *= (double)0.7f;
        }
        this.setSpriteForAge(this.spriteProvider);
        if (this.age > this.maxAge / 2) {
            this.setAlpha(((float) this.maxAge/2-(float)this.age)/this.maxAge);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void setTargetColor(int rgbHex) {
        super.setTargetColor(rgbHex);
    }

    public static class Factory implements ParticleFactory<DefaultParticleType>{
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider){
            this.spriteProvider = spriteProvider;
        }
        public Particle createParticle(DefaultParticleType particleType,ClientWorld world,
                                       double x, double y,double z,double xd, double yd ,double zd,float upwards){
            return new ThickTrailParticle(world,x,y,z,this.spriteProvider,0f,0,0,0);
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ThickTrailParticle(world,x,y,z,this.spriteProvider,0f,velocityX,velocityY,velocityZ);
        }
    }
}
