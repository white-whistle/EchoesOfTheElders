package com.bajookie.biotech.item;

import com.bajookie.biotech.BioTech;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class ModItems {
    //items list
    public static final Item FLUID_CELL = registerItem("fluid_cell",new Item(new FabricItemSettings()));
    public static final Item SULFUR = registerItem("sulfur",new Item(new FabricItemSettings()));
    public static final Item ORANGE = registerItem("orange",new Item(new FabricItemSettings().food(ModFoodComponents.ORANGE)));
    public static final Item LEMON = registerItem("lemon",new Item(new FabricItemSettings().food(ModFoodComponents.LEMON)));

    //Register methods
    public static Item registerItem(String name,Item item){
        return Registry.register(Registries.ITEM,new Identifier(MOD_ID,name),item);
    }
    public static void registerModItems(){
        BioTech.LOGGER.info("Register Items for:"+MOD_ID);
    }
}
