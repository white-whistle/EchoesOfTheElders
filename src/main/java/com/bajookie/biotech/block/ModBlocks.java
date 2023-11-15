package com.bajookie.biotech.block;

import com.bajookie.biotech.BioTech;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class ModBlocks {
    public static final Block FUEL = registerBlock("fuel_block", new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        BioTech.LOGGER.info("Registering blocks for ---> " + MOD_ID);
    }
}
