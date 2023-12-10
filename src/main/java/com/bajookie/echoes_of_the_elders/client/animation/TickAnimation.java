package com.bajookie.echoes_of_the_elders.client.animation;

public class TickAnimation {
    int ticks;
    int currentTicks;

    public TickAnimation(int ticks) {
        this.ticks = ticks;
        this.currentTicks = ticks;
    }

    public void tick() {
        if (this.currentTicks < this.ticks) {
            this.currentTicks++;
        }
    }

    public void start() {
        this.currentTicks = 0;
    }

    public float getProgress(float partialTicks) {
        return Math.min((this.currentTicks + partialTicks) / (float) this.ticks, 1);
    }

    public boolean isActive() {
        return this.currentTicks < this.ticks;
    }
}
