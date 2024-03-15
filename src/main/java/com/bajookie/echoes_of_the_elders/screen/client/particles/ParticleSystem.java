package com.bajookie.echoes_of_the_elders.screen.client.particles;

import java.util.ArrayList;

public class ParticleSystem<T extends ParticleSystem.Particle> {

    public ArrayList<T> particles = new ArrayList<>();

    public void tick() {
        particles.forEach(Particle::tick);
        particles.removeIf(Particle::isDead);
    }

    public interface Particle {
        void tick();

        boolean isDead();
    }
}
