package com.bajookie.echoes_of_the_elders.mixin;

import net.minecraft.entity.player.ItemCooldownManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemCooldownManager.Entry.class)
public interface ItemCooldownManagerEntryAccessor {
    @Accessor()
    int getStartTick();

    @Accessor()
    void setStartTick(int v);

    @Accessor()
    int getEndTick();

    @Accessor()
    void setEndTick(int v);

    @Invoker("<init>")
    static ItemCooldownManager.Entry createEntry(int startTick, int endTick) {
        return null;
    }
}
