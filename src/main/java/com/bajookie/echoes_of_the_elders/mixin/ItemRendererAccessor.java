package com.bajookie.echoes_of_the_elders.mixin;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {
    @Accessor("models")
    ItemModels getModels();

    @Accessor("builtinModelItemRenderer")
    BuiltinModelItemRenderer getBuiltinModelItemRenderer();
}
