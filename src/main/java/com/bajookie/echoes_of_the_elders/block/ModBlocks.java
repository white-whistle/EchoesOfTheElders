package com.bajookie.echoes_of_the_elders.block;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.custom.BearTrapBlock;
import com.bajookie.echoes_of_the_elders.block.custom.MinersFruitBlock;
import com.bajookie.echoes_of_the_elders.block.custom.NetherFruitBlock;
import com.bajookie.echoes_of_the_elders.block.custom.RelicContainerBlock;
import com.bajookie.echoes_of_the_elders.item.ModFoodComponents;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block BEAR_TRAP_BLOCK = registerBlock("bear_trap_block", new BearTrapBlock(FabricBlockSettings.copyOf(Blocks.ANVIL).nonOpaque().notSolid()));
    public static final Block RELIC_CONTAINER_BLOCK = registerBlock("artifact_vault_block", new RelicContainerBlock(FabricBlockSettings.copyOf(Blocks.STONE).requiresTool().strength(100000).hardness(-1).nonOpaque()));
    public static final Block EXPLORER_FRUIT_BLOCK = registerBlock("explorers_fruit_block", new FlowerBlock(StatusEffects.GLOWING, 1, FabricBlockSettings.copyOf(Blocks.ALLIUM).nonOpaque().noCollision()));
    public static final Block MINERS_FRUIT_BLOCK = registerBlock("miners_fruit_block", new MinersFruitBlock(), new FabricItemSettings().food(ModFoodComponents.MINERS_FRUIT));
    public static final Block NETHER_FRUIT_BLOCK = registerBlock("nether_fruit_block", new NetherFruitBlock(), new FabricItemSettings().food(ModFoodComponents.NETHER_FRUIT));
    public static final Block POTTED_EXPLORER_FRUIT_BLOCK = Registry.register(Registries.BLOCK, new Identifier(EOTE.MOD_ID, "potted_explorers_fruit_block"), new FlowerPotBlock(EXPLORER_FRUIT_BLOCK, FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));
    public static final Block POTTED_MINERS_FRUIT_BLOCK = Registry.register(Registries.BLOCK, new Identifier(EOTE.MOD_ID, "potted_miners_fruit_block"), new FlowerPotBlock(MINERS_FRUIT_BLOCK, FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        return registerBlock(name, block, new FabricItemSettings());
    }

    private static Block registerBlock(String name, Block block, FabricItemSettings settings) {
        // register block item
        Registry.register(Registries.ITEM, new Identifier(EOTE.MOD_ID, name), new BlockItem(block, settings));
        return Registry.register(Registries.BLOCK, new Identifier(EOTE.MOD_ID, name), block);
    }


    public static void registerModBlocks() {
        EOTE.LOGGER.info("Registering blocks for ---> " + EOTE.MOD_ID);
    }

    public static void registerModBlocksModelLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(EXPLORER_FRUIT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MINERS_FRUIT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(NETHER_FRUIT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(POTTED_EXPLORER_FRUIT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(POTTED_MINERS_FRUIT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RELIC_CONTAINER_BLOCK, RenderLayer.getCutout());
    }
}
