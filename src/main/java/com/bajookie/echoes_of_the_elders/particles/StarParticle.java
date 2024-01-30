package com.bajookie.echoes_of_the_elders.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;

public class StarParticle extends SpriteBillboardParticle {
    public StarParticle(ClientWorld clientWorld, double xCoord, double yCoord, double zCoord,
                             SpriteProvider spriteProvider, double xd, double yd, double zd) {
        super(clientWorld, xCoord, yCoord, zCoord, xd, yd, zd);
        this.maxAge = 2;
        this.setVelocity(0,0,0);
        this.scale(3f);
        this.setSpriteForAge(spriteProvider);

    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public Particle scale(float scale) {
        return super.scale(3f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider){
            this.spriteProvider = spriteProvider;
        }
        public Particle createParticle(DefaultParticleType particleType, ClientWorld world,
                                       double x, double y, double z, double xd, double yd , double zd){
            return new StarParticle(world,x,y,z,this.spriteProvider,xd,yd,zd);
        }
    }
}
