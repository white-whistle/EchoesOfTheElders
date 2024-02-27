package com.bajookie.echoes_of_the_elders.client.render;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.IStackPredicate;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ItemStackCustomArmorTextures {
    public static HashMap<Item, TriFunction<ItemStack, EquipmentSlot, LivingEntity, Identifier>> ITEMSTACK_CUSTOM_ARMOR_TEXTURE_IDENTIFIER = new HashMap<>();

    static {
        ITEMSTACK_CUSTOM_ARMOR_TEXTURE_IDENTIFIER.put(ModItems.GUNHEELS, (stack, equipmentSlot, livingEntity) -> new ModIdentifier("gunheel" + IStackPredicate.stackAppendix(stack)));
        ITEMSTACK_CUSTOM_ARMOR_TEXTURE_IDENTIFIER.put(ModItems.GANGWAY, (stack, equipmentSlot, livingEntity) -> new ModIdentifier("gangway" + IStackPredicate.stackAppendix(stack)));
    }

    @Nullable
    public static Identifier get(ItemStack itemStack, EquipmentSlot slot, LivingEntity wearer, int layer) {
        var ret = getBaseTexture(itemStack, slot, wearer);
        if (ret == null) return null;

        return toArmorLayerTexture(ret, layer);
    }

    public static Identifier toArmorLayerTexture(Identifier identifier, int layer) {
        return identifier.withPrefixedPath("textures/models/armor/").withSuffixedPath("_layer_" + layer + ".png");
    }

    @Nullable
    public static Identifier getBaseTexture(ItemStack itemStack, EquipmentSlot slot, LivingEntity wearer) {
        var consumer = ITEMSTACK_CUSTOM_ARMOR_TEXTURE_IDENTIFIER.get(itemStack.getItem());
        if (consumer == null) return null;

        return consumer.apply(itemStack, slot, wearer);
    }
}
