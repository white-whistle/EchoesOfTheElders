package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.PLANKS).add(ModBlocks.ANCIENT_TREE_PLANKS.asItem());
        getOrCreateTagBuilder(ItemTags.LOGS).add(ModBlocks.ANCIENT_TREE_LOG.asItem())
                .add(ModBlocks.ANCIENT_TREE_WOOD.asItem())
                .add(ModBlocks.STRIPPED_ANCIENT_TREE_LOG.asItem())
                .add(ModBlocks.STRIPPED_ANCIENT_TREE_WOOD.asItem());
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).add(ModBlocks.ANCIENT_TREE_LOG.asItem())
                .add(ModBlocks.ANCIENT_TREE_WOOD.asItem())
                .add(ModBlocks.STRIPPED_ANCIENT_TREE_LOG.asItem())
                .add(ModBlocks.STRIPPED_ANCIENT_TREE_WOOD.asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(ModBlocks.WOOL_TENT_DOOR.asItem());
    }
}