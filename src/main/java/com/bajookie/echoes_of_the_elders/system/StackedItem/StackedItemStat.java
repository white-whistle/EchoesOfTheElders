package com.bajookie.echoes_of_the_elders.system.StackedItem;

import net.minecraft.item.ItemStack;

public abstract class StackedItemStat<T> {

    public final T min;
    public final T max;

    public StackedItemStat(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public float getStackProgress(ItemStack stack) {
        return (stack.getCount() - 1) / (float) (stack.getMaxCount() - 1);
    }

    public abstract T get(ItemStack stack);

    public static class Float extends StackedItemStat<java.lang.Float> {
        public Float(java.lang.Float min, java.lang.Float max) {
            super(min, max);
        }

        @Override
        public java.lang.Float get(ItemStack stack) {
            float progress = this.getStackProgress(stack);
            return this.min + ((this.max - this.min) * progress);
        }
    }

    public static class Int extends StackedItemStat<java.lang.Integer> {
        public Int(java.lang.Integer min, java.lang.Integer max) {
            super(min, max);
        }

        @Override
        public java.lang.Integer get(ItemStack stack) {
            float progress = this.getStackProgress(stack);
            return (int) (this.min + ((this.max - this.min) * progress));
        }
    }
}
