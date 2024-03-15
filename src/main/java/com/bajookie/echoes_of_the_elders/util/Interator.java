package com.bajookie.echoes_of_the_elders.util;

import org.jetbrains.annotations.NotNull;

public record Interator(int size) implements Iterable<Integer> {

    public static Interator of(int size) {
        return new Interator(size);
    }

    @NotNull
    @Override
    public java.util.Iterator<Integer> iterator() {
        return new Iterator(this.size);
    }

    private static class Iterator implements java.util.Iterator<Integer> {
        public int index = 0;
        public final int size;

        public Iterator(int size) {
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return index < size - 1;
        }

        @Override
        public Integer next() {
            return index++;
        }
    }
}
