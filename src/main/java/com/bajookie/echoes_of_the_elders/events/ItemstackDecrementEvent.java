package com.bajookie.echoes_of_the_elders.events;

import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public interface ItemstackDecrementEvent {
    interface ModItemstackExtension {
        void echoesOfTheElders$setDecrementHandler(Consumer<ItemStack> consumer);
    }

}
