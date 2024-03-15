package com.bajookie.echoes_of_the_elders.screen.client.particles;

public abstract class LifetimeParticle extends ScreenParticle {
    int lifetime;
    int currentLifetime;


    public LifetimeParticle(int lifetime) {
        this.lifetime = lifetime;
        this.currentLifetime = lifetime;
    }

    @Override
    public void tick() {
        currentLifetime -= 1;
    }

    @Override
    public boolean isDead() {
        return this.currentLifetime <= 0;
    }

    public float getProgress() {
        return 1 - (Math.max(0, this.currentLifetime) / (float) this.lifetime);
    }
}