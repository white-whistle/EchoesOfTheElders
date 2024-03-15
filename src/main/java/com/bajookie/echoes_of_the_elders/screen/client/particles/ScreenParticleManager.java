package com.bajookie.echoes_of_the_elders.screen.client.particles;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.Random;

public class ScreenParticleManager implements ScreenEvents.AfterRender, ScreenEvents.AfterTick {

    public static ScreenParticleManager INSTANCE = new ScreenParticleManager();

    public ParticleSystem<ScreenParticle> particleSystem = new ParticleSystem<>();
    public Vector2i mousePos = new Vector2i();
    public Random random = new Random();

    public void init() {
        ScreenEvents.AFTER_INIT.register((mc, screen, scaledWidth, scaledHeight) -> {
            ScreenParticleManager.this.setupScreen(screen);
        });
    }

    public void setupScreen(Screen screen) {
        ScreenEvents.afterRender(screen).register(this);
        ScreenEvents.afterTick(screen).register(this);
        ScreenEvents.remove(screen).register(this::teardownScreen);
    }

    public void teardownScreen(Screen screen) {
        particleSystem.particles.clear();
    }


    @Override
    public void afterRender(Screen screen, DrawContext drawContext, int mouseX, int mouseY, float tickDelta) {
        mousePos.set(mouseX, mouseY);
        particleSystem.particles.forEach(p -> p.render(screen, drawContext, mouseX, mouseY, tickDelta));
    }

    @Override
    public void afterTick(Screen screen) {
        particleSystem.tick();
    }

    public static void addParticle(ScreenParticle particle) {
        INSTANCE.particleSystem.particles.add(particle);
    }

    public static Vector2f getMousePos() {
        return new Vector2f(INSTANCE.mousePos);
    }
}
