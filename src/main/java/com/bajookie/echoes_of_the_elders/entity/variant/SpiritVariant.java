package com.bajookie.echoes_of_the_elders.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum SpiritVariant {
    DEFAULT(0),
    GREY(1);

    private static final SpiritVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(
            SpiritVariant::getId)).toArray(SpiritVariant[]::new);
    private final int id;
    SpiritVariant(int id){this.id = id;}

    public int getId() {
        return id;
    }
    public static SpiritVariant byId(int id){return BY_ID[id% BY_ID.length];}
}
