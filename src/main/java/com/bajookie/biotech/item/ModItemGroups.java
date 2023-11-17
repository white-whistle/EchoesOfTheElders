package com.bajookie.biotech.item;

import com.bajookie.biotech.BioTech;
import com.bajookie.biotech.block.ModBlocks;
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
                entries.add(ModItems.UNFERMENTEDBIOMASS);
                entries.add(ModItems.FERMENTEDBIOMASS);
                entries.add(ModItems.MORTAR_AND_PESTLE);
                entries.add(ModItems.CITRUSJUICE);
                entries.add(ModItems.EXPLORER_FRUIT);
                //blocks
                entries.add(ModBlocks.BEAR_TRAP_BLOCK);
                //flowers
                entries.add(ModBlocks.EXPLORER_FRUIT_BLOCK);
            }).build());
    public static void registerGroups(){
        BioTech.LOGGER.info("Registering Item Groups for ---> "+MOD_ID);
    }
}
