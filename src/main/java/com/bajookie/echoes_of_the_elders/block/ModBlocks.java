package com.bajookie.echoes_of_the_elders.block;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.custom.*;
import com.bajookie.echoes_of_the_elders.item.ModFoodComponents;
import com.bajookie.echoes_of_the_elders.world.tree.AncientTreeSaplingGenerator;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block BEAR_TRAP_BLOCK = registerBlock("bear_trap_block", new BearTrapBlock(FabricBlockSettings.copyOf(Blocks.ANVIL).nonOpaque().notSolid()));
    public static final Block ARTIFACT_VAULT = registerBlock("artifact_vault_block", new RelicContainerBlock(FabricBlockSettings.copyOf(Blocks.STONE).requiresTool().strength(100000).hardness(-1).nonOpaque()));
    public static final Block EXPLORER_FRUIT_BLOCK = registerBlock("explorers_fruit_block", new FlowerBlock(StatusEffects.GLOWING, 1, FabricBlockSettings.copyOf(Blocks.ALLIUM).nonOpaque().noCollision()));
    public static final Block MINERS_FRUIT_BLOCK = registerBlock("miners_fruit_block", new MinersFruitBlock(), new FabricItemSettings().food(ModFoodComponents.MINERS_FRUIT));
    public static final Block NETHER_FRUIT_BLOCK = registerBlock("nether_fruit_block", new NetherFruitBlock(), new FabricItemSettings().food(ModFoodComponents.NETHER_FRUIT));
    public static final Block POTTED_EXPLORER_FRUIT_BLOCK = Registry.register(Registries.BLOCK, new Identifier(EOTE.MOD_ID, "potted_explorers_fruit_block"), new FlowerPotBlock(EXPLORER_FRUIT_BLOCK, FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));
    public static final Block POTTED_MINERS_FRUIT_BLOCK = Registry.register(Registries.BLOCK, new Identifier(EOTE.MOD_ID, "potted_miners_fruit_block"), new FlowerPotBlock(MINERS_FRUIT_BLOCK, FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));


    //Decorative blocks
    public static final Block SUN_RUNE_BLOCK = registerBlock("sun_rune_block", new SunRuneBlock());
    public static final Block CHISELED_MOSSY_STONE = registerBlock("chiseled_mossy_stone", new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS)));
    //Logs and Wood
    public static final Block ANCIENT_TREE_LOG = registerBlock("ancient_tree_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));
    public static final Block ANCIENT_TREE_WOOD = registerBlock("ancient_tree_wood", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));
    public static final Block STRIPPED_ANCIENT_TREE_LOG = registerBlock("stripped_ancient_tree_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG)));
    public static final Block STRIPPED_ANCIENT_TREE_WOOD = registerBlock("stripped_ancient_tree_wood", new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD)));
    public static final Block ANCIENT_TREE_PLANKS = registerBlock("ancient_tree_planks", new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block ANCIENT_TREE_LEAVES = registerBlock("ancient_tree_leaves", new AncientTreeLeaves(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).nonOpaque()));
    public static final Block ANCIENT_TREE_SAPLING = registerBlock("ancient_tree_sapling", new SaplingBlock(new AncientTreeSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));

    //Foliage
    public static final Block ELDER_LILY_FLOWER = registerBlock("elder_lily_flower", new FlowerBlock(StatusEffects.GLOWING, 1, FabricBlockSettings.copyOf(Blocks.ALLIUM).nonOpaque().noCollision().breakInstantly()));
    public static final Block POTTED_ELDER_LILY_FLOWER = Registry.register(Registries.BLOCK, new Identifier(EOTE.MOD_ID, "potted_elder_lily_flower"), new FlowerPotBlock(ELDER_LILY_FLOWER, FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));
    public static final Block SPIRITAL_GRASS = registerBlock("spirital_grass",new SpiritalGrass(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).burnable().pistonBehavior(PistonBehavior.DESTROY)));

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
        BlockRenderLayerMap.INSTANCE.putBlock(ARTIFACT_VAULT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ANCIENT_TREE_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ANCIENT_TREE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ELDER_LILY_FLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(POTTED_ELDER_LILY_FLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SPIRITAL_GRASS,RenderLayer.getCutout());
    }
}
