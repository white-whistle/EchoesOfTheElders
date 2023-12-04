package com.bajookie.biotech.util;

import com.bajookie.biotech.block.ModBlocks;
import com.bajookie.biotech.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerTintableCross(ModBlocks.NETHER_FRUIT_BLOCK, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.EXPLORER_FRUIT_BLOCK,ModBlocks.POTTED_EXPLORER_FRUIT_BLOCK, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.MINERS_FRUIT_BLOCK,ModBlocks.POTTED_MINERS_FRUIT_BLOCK, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerSimpleState(ModBlocks.BEAR_TRAP_BLOCK);
        blockStateModelGenerator.registerSimpleState(ModBlocks.RELIC_CONTAINER_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ANCIENT_STONE_SWORD,Models.HANDHELD);
        itemModelGenerator.register(ModItems.SHINY_ANCIENT_STONE_SWORD,Models.HANDHELD);
        itemModelGenerator.register(ModItems.VITALITY_PUMP,Models.GENERATED);
        itemModelGenerator.register(ModItems.RADIANT_LOTUS,Models.GENERATED);
        itemModelGenerator.register(ModItems.PORTAL_RING,Models.GENERATED);

    }
}