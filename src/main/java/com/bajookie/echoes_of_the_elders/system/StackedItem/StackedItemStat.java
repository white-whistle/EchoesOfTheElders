package com.bajookie.echoes_of_the_elders.system.StackedItem;

import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import net.minecraft.item.ItemStack;

public abstract class StackedItemStat<T> {

    public final T min;
    public final T max;

    public StackedItemStat(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public abstract T get(float progress);

    public T get(ItemStack stack) {
        float progress = StackLevel.getStackProgress(stack);
        return this.get(progress);
    }

    public static class Float extends StackedItemStat<java.lang.Float> {
        public Float(java.lang.Float min, java.lang.Float max) {
            super(min, max);
        }

        @Override
        public java.lang.Float get(float progress) {
            return this.min + ((this.max - this.min) * progress);
        }
    }

    public static class Int extends StackedItemStat<java.lang.Integer> {
        public Int(java.lang.Integer min, java.lang.Integer max) {
            super(min, max);
        }

        @Override
        public java.lang.Integer get(float progress) {
            return (int) (this.min + ((this.max - this.min) * progress));
        }
    }
}
