package com.bajookie.echoes_of_the_elders.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class GaleCritParticle extends SpriteBillboardParticle {
    protected GaleCritParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    protected GaleCritParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
        float j;
        this.velocityMultiplier = 0.7f;
        this.gravityStrength = 0.5f;
        this.velocityX *= (double) 0.1f;
        this.velocityY *= (double) 0.1f;
        this.velocityZ *= (double) 0.1f;
        this.velocityX += g * 0.4;
        this.velocityY += h * 0.4;
        this.velocityZ += i * 0.4;
        this.red = j = (float) (Math.random() * (double) 0.3f + (double) 0.6f);
        this.green = j;
        this.blue = j;
        this.scale *= 0.75f;
        this.maxAge = Math.max((int) (6.0 / (Math.random() * 0.8 + 0.6)), 1);
        this.collidesWithWorld = false;
        this.tick();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(value = EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            GaleCritParticle damageParticle = new GaleCritParticle(clientWorld, d, e, f, g, h, i);
            damageParticle.setSprite(this.spriteProvider);
            return damageParticle;
        }
    }
}
