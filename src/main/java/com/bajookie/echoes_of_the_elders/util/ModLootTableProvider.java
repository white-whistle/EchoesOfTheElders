package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.loot.entry.ItemEntry;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.EXPLORER_FRUIT_BLOCK, BlockLootTableGenerator.dropsWithShears(ModBlocks.EXPLORER_FRUIT_BLOCK, ItemEntry.builder(ModItems.EXPLORER_FRUIT)));
        addDrop(ModBlocks.MINERS_FRUIT_BLOCK);
        addDrop(ModBlocks.POTTED_EXPLORER_FRUIT_BLOCK, ModBlocks.EXPLORER_FRUIT_BLOCK);
        addDrop(ModBlocks.POTTED_MINERS_FRUIT_BLOCK, ModBlocks.MINERS_FRUIT_BLOCK);
        addDrop(ModBlocks.ARTIFACT_VAULT);
        addDrop(ModBlocks.ANCIENT_TREE_LOG);
        addDrop(ModBlocks.ANCIENT_TREE_WOOD);
        addDrop(ModBlocks.STRIPPED_ANCIENT_TREE_LOG);
        addDrop(ModBlocks.STRIPPED_ANCIENT_TREE_WOOD);
        addDrop(ModBlocks.ANCIENT_TREE_PLANKS);
    }


    //    public LootTable.Builder copperLikeOreDrops(Block drop, Item item, float minLoot, float maxLoot) {
    //        var setLootCountFn = SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 5.0f));
    //        var enableOreFortuneFn = ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE);
    //
    //        var lootEntry = ItemEntry.builder(item);
    //
    //        // set drop amt
    //        lootEntry = lootEntry.apply(setLootCountFn);
    //
    //        // add fortune logic
    //        lootEntry = lootEntry.apply(enableOreFortuneFn);
    //
    //        // add explosion logic
    //        lootEntry = this.applyExplosionDecay(drop, lootEntry);
    //
    //        // add silk touch logic + return loot table
    //        return BlockLootTableGenerator.dropsWithSilkTouch(drop, lootEntry);
    //    }
}