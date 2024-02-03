package com.bajookie.echoes_of_the_elders.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;

public class EarthSpikeParticle extends AnimatedParticle {

    public EarthSpikeParticle(ClientWorld clientWorld, double xCoord, double yCoord, double zCoord,
                              SpriteProvider spriteProvider, double xd, double yd, double zd) {
        this(clientWorld, xCoord, yCoord, zCoord, spriteProvider, 0);
    }

    public EarthSpikeParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration) {
        super(world, x, y+0.7, z, spriteProvider, upwardsAcceleration);
        this.maxAge = 20;
        this.scale = 1f;
        this.setSpriteForAge(spriteProvider);
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
        return super.scale(1f);
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld world,
                                       double x, double y, double z, double xd, double yd, double zd) {
            return new EarthSpikeParticle(world, x, y, z, this.spriteProvider, xd, yd, zd);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
}
