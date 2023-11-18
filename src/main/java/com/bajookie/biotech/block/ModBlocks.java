package com.bajookie.biotech.block;

import com.bajookie.biotech.BioTech;
import com.bajookie.biotech.block.custom.BearTrapBlock;
import com.bajookie.biotech.block.custom.MinersFruitBlock;
import com.bajookie.biotech.block.custom.RelicContainerBlock;
import com.bajookie.biotech.item.ModFoodComponents;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class ModBlocks {
    public static final Block BEAR_TRAP_BLOCK = registerBlock("bear_trap_block",new BearTrapBlock(FabricBlockSettings.copyOf(Blocks.ANVIL).nonOpaque().notSolid()));
    public static final Block RELIC_CONTAINER_BLOCK = registerBlock("artifact_vault_block",new RelicContainerBlock(FabricBlockSettings.copyOf(Blocks.STONE).requiresTool().strength(3).nonOpaque()));
    public static final Block EXPLORER_FRUIT_BLOCK = registerBlock("explorers_fruit_block",new FlowerBlock(StatusEffects.GLOWING,1,FabricBlockSettings.copyOf(Blocks.ALLIUM).nonOpaque().noCollision()));
    public static final Block MINERS_FRUIT_BLOCK = registerBlock("miners_fruit_block",new MinersFruitBlock(),new FabricItemSettings().food(ModFoodComponents.MINERS_FRUIT));
    public static final Block POTTED_EXPLORER_FRUIT_BLOCK = Registry.register(Registries.BLOCK,new Identifier(MOD_ID,"potted_explorers_fruit_block"),new FlowerPotBlock(EXPLORER_FRUIT_BLOCK,FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));
    public static final Block POTTED_MINERS_FRUIT_BLOCK = Registry.register(Registries.BLOCK,new Identifier(MOD_ID,"potted_miners_fruit_block"),new FlowerPotBlock(MINERS_FRUIT_BLOCK,FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        return registerBlock(name, block,new FabricItemSettings());
    }

    private static Block registerBlock(String name, Block block,FabricItemSettings settings) {
        // register block item
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), new BlockItem(block,settings));
        return Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
    }


    public static void registerModBlocks() {
        BioTech.LOGGER.info("Registering blocks for ---> " + MOD_ID);
    }
}
