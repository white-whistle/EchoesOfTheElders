package com.bajookie.echoes_of_the_elders.item.custom;


public interface IArtifact {
    default boolean shouldDrop() {
        return true;
    }
}
