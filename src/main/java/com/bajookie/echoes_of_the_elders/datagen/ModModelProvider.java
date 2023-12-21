package com.bajookie.echoes_of_the_elders.datagen;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.IHasUpscaledModel;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

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
                ModItems.DOOMSTICK_ITEM,
                ModItems.GODSLAYER
        );
        protected static final List<Item> SKIP = List.of(
                ModItems.WITHER_SCALES_ITEM,
                ModItems.CHAIN_LIGHTNING_ITEM,
                ModItems.REALITY_PICK
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        var HANDHELD_X32 = new Model(Optional.of(new Identifier(EOTE.MOD_ID, "item/handheld_32")), Optional.empty(), TextureKey.LAYER0);

        ModItems.registeredModItems.forEach(item -> {

            // skip model gen (items with hand-made jsons)
            if (ItemModelConfig.SKIP.contains(item)) {
                return;
            }

            // handheld models
            if (ItemModelConfig.HANDHELD.contains(item)) {
                itemModelGenerator.register(item, Models.HANDHELD);

                if (item instanceof IHasUpscaledModel iHasUpscaledModel) {
                    var upscaledIdentifier = new Identifier(EOTE.MOD_ID, "item/" + iHasUpscaledModel.getUpscaledModel());

                    HANDHELD_X32.upload(upscaledIdentifier, TextureMap.layer0(upscaledIdentifier), itemModelGenerator.writer);
                }

                return;
            }

            // by default register as generated
            itemModelGenerator.register(item, Models.GENERATED);
        });
    }
}