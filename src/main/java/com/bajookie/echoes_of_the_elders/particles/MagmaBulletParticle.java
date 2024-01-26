package com.bajookie.echoes_of_the_elders.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class MagmaBulletParticle extends SpriteBillboardParticle {
    private SpriteProvider spriteProvider;
    protected MagmaBulletParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }
    public MagmaBulletParticle(ClientWorld clientWorld, double xCoord, double yCoord, double zCoord,
                             SpriteProvider spriteProvider, double xd, double yd, double zd) {
        super(clientWorld, xCoord, yCoord, zCoord, xd, yd, zd);
        this.maxAge = 5;
        this.spriteProvider = spriteProvider;
        this.scale(2);
        this.setVelocity(0,0,0);
        this.setSpriteForAge(spriteProvider);
    }
    @Override
    public void setSpriteForAge(SpriteProvider spriteProvider) {
        super.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
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
            return new MagmaBulletParticle(world,x,y,z,this.spriteProvider,xd,yd,zd);
        }
    }
}
