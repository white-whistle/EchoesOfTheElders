package com.bajookie.echoes_of_the_elders.client.render;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class AdditionalArmorLayers {
    public static HashMap<Item, BlockStateVariantMap.QuadFunction<ItemStack, EquipmentSlot, LivingEntity, Integer, RenderLayer>> ADDITIONAL_ARMOR_LAYERS = new HashMap<>();

    static {
        ADDITIONAL_ARMOR_LAYERS.put(ModItems.GUNHEELS, GlowLayer::getGlowFromBaseTexture);
    }

    @Nullable
    public static RenderLayer get(ItemStack itemStack, EquipmentSlot slot, LivingEntity wearer, int layer) {
        var consumer = ADDITIONAL_ARMOR_LAYERS.get(itemStack.getItem());
        if (consumer == null) return null;

        return consumer.apply(itemStack, slot, wearer, layer);
    }
}
