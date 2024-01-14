package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.PLANKS).add(ModBlocks.ANCIENT_TREE_PLANKS);
        getOrCreateTagBuilder(BlockTags.LOGS).add(ModBlocks.ANCIENT_TREE_LOG)
                .add(ModBlocks.ANCIENT_TREE_WOOD)
                .add(ModBlocks.STRIPPED_ANCIENT_TREE_LOG)
                .add(ModBlocks.STRIPPED_ANCIENT_TREE_WOOD);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(ModBlocks.ANCIENT_TREE_LOG)
                .add(ModBlocks.ANCIENT_TREE_WOOD)
                .add(ModBlocks.STRIPPED_ANCIENT_TREE_LOG)
                .add(ModBlocks.STRIPPED_ANCIENT_TREE_WOOD);
        getOrCreateTagBuilder(BlockTags.LEAVES).add(ModBlocks.ANCIENT_TREE_LEAVES);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(ModBlocks.WOOL_TENT_DOOR);
    }
}