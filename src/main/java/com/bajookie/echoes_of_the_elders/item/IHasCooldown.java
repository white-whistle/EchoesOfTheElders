package com.bajookie.echoes_of_the_elders.item;

public interface IHasCooldown {
    int getCooldown();

    default int getMinCooldown() {
        return 1;
    }

    default boolean canReduceCooldown() {
        return true;
    }
}
