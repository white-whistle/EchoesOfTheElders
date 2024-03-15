package com.bajookie.echoes_of_the_elders.screen.client.particles;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

import java.util.Random;

public abstract class ScreenParticle implements ParticleSystem.Particle {

    public static Random random = new Random();

    public abstract void render(Screen screen, DrawContext drawContext, int mouseX, int mouseY, float tickDelta);
}
