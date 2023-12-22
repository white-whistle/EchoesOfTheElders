package com.bajookie.echoes_of_the_elders.system.StackedItem;

import java.util.HashMap;
import java.util.function.Function;

public class StackedLazyGenerator<T> {
    private final HashMap<Integer, T> values = new HashMap<>();
    private final Function<Integer, T> generator;

    public StackedLazyGenerator(Function<Integer, T> generator) {
        this.generator = generator;
    }

    public T get(int index) {
        var v = values.get(index);

        if (v != null) return v;

        v = generator.apply(index);
        values.put(index, v);

        return v;
    }
}
