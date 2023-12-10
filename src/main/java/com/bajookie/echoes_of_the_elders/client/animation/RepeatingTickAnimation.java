package com.bajookie.echoes_of_the_elders.client.animation;

public class RepeatingTickAnimation extends TickAnimation {
    public RepeatingTickAnimation(int ticks) {
        super(ticks);
        this.currentTicks = 0;
    }

    public void tick() {

        this.currentTicks++;

        if (this.currentTicks >= this.ticks) this.currentTicks -= this.ticks;

    }
}
