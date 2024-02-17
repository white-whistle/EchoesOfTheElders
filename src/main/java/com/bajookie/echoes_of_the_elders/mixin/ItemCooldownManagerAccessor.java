package com.bajookie.echoes_of_the_elders.mixin;

import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ItemCooldownManager.class)
public interface ItemCooldownManagerAccessor {

    @Accessor()
    int getTick();

    @Accessor()
    void setTick(int v);

    @Accessor("entries")
    Map<Item, ItemCooldownManager.Entry> getEntries();
}
