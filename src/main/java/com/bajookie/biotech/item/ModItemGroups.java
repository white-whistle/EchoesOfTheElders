package com.bajookie.biotech.item;

import com.bajookie.biotech.BioTech;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class ModItemGroups {
    public static final ItemGroup MOD_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,new Identifier(MOD_ID,"mod_item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.bio")).icon(()->new ItemStack(ModItems.FLUID_CELL)).entries((displayContext, entries) -> {
                entries.add(ModItems.FLUID_CELL);
                entries.add(ModItems.SULFUR);
                entries.add(ModItems.LEMON);
                entries.add(ModItems.ORANGE);
            }).build());
    public static void registerGroups(){
        BioTech.LOGGER.info("Registering Item Groups for ---> "+MOD_ID);
    }
}
