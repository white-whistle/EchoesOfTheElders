package com.bajookie.echoes_of_the_elders.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;

public class ElectricShockParticle extends AnimatedParticle {
    private Vec3d targetPos = new Vec3d(0,0,0);
    public ElectricShockParticle(ClientWorld clientWorld, double xCoord, double yCoord, double zCoord,
                             SpriteProvider spriteProvider, double xd, double yd, double zd) {
        this(clientWorld, xCoord, yCoord, zCoord,spriteProvider,0);
        this.maxAge = 15;
        this.targetPos = new Vec3d(xd,yd,zd);
        this.setSpriteForAge(spriteProvider);

    }
    public ElectricShockParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration) {
        super(world, x, y, z, spriteProvider, upwardsAcceleration);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void setAlpha(float alpha) {
        super.setAlpha(alpha);
    }

    @Override
    public Particle scale(float scale) {
        return super.scale(0.02f);
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider){
            this.spriteProvider = spriteProvider;
        }
        public Particle createParticle(DefaultParticleType particleType,ClientWorld world,
                                       double x, double y,double z,double xd, double yd ,double zd){
            return new ElectricShockParticle(world,x,y,z,this.spriteProvider,xd,yd,zd);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
}
