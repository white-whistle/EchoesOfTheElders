package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

import java.util.List;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerTintableCross(ModBlocks.NETHER_FRUIT_BLOCK, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.EXPLORER_FRUIT_BLOCK, ModBlocks.POTTED_EXPLORER_FRUIT_BLOCK, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.MINERS_FRUIT_BLOCK, ModBlocks.POTTED_MINERS_FRUIT_BLOCK, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerSimpleState(ModBlocks.BEAR_TRAP_BLOCK);
        blockStateModelGenerator.registerSimpleState(ModBlocks.RELIC_CONTAINER_BLOCK);
    }

    static class ItemModelConfig {
        protected static final List<Item> HANDHELD = List.of(
                ModItems.ANCIENT_STONE_SWORD,
                ModItems.SHINY_ANCIENT_STONE_SWORD,
                ModItems.MIDAS_HAMMER,
                ModItems.DAGON
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ModItems.registeredModItems.forEach(item -> {
            // handheld models
            if (ItemModelConfig.HANDHELD.contains(item)) {
                itemModelGenerator.register(item, Models.HANDHELD);
                return;
            }

            // by default register as generated
            itemModelGenerator.register(item, Models.GENERATED);
        });
    }
}