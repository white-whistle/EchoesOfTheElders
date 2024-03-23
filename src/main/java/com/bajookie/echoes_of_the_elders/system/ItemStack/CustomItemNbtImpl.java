package com.bajookie.echoes_of_the_elders.system.ItemStack;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class CustomItemNbtImpl<T> {

    public abstract T get(ItemStack itemStack);

    public abstract ItemStack set(ItemStack itemStack, T v);

    public static class Simple<T> extends CustomItemNbtImpl<T> {

        protected final String key;
        protected final Function<NbtCompound, T> getter;
        protected final BiConsumer<NbtCompound, T> setter;
        protected final T defaultValue;

        public Simple(String key, T defaultValue, Function<NbtCompound, T> getter, BiConsumer<NbtCompound, T> setter) {
            this.key = key;
            this.getter = getter;
            this.setter = setter;
            this.defaultValue = defaultValue;
        }

        @Override
        public T get(ItemStack itemStack) {
            var nbt = itemStack.getNbt();
            if (nbt == null) return this.defaultValue;
            if (!nbt.contains(key)) return this.defaultValue;

            return this.getter.apply(nbt);
        }

        @Override
        public ItemStack set(ItemStack itemStack, T v) {
            var nbt = itemStack.getOrCreateNbt();

            this.setter.accept(nbt, v);

            return itemStack;
        }

        public ItemStack update(ItemStack itemStack, Function<T, T> updater) {
            set(itemStack, updater.apply(get(itemStack)));

            return itemStack;
        }


    }

    public static class Boolean extends Simple<java.lang.Boolean> {

        public Boolean(String key, boolean defaultValue) {
            super(key, defaultValue, nbt -> nbt.getBoolean(key), (nbt, v) -> nbt.putBoolean(key, v));
        }

    }

    public static class Int extends Simple<java.lang.Integer> {

        public Int(String key, int defaultValue) {
            super(key, defaultValue, nbt -> nbt.getInt(key), (nbt, v) -> nbt.putInt(key, v));
        }

    }

}
