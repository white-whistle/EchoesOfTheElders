package com.bajookie.echoes_of_the_elders.mixin;

import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BuiltinModelItemRenderer.class)
public interface BuiltinModelItemRendererAccessor {
    @Accessor("entityModelLoader")
    EntityModelLoader getEntityModelLoader();
}
