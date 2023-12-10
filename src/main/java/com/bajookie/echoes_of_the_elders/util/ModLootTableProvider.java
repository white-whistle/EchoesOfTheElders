package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

import java.util.Arrays;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        //addDrop(ModBlocks.RUBY_BLOCK);//drops itself
        //addDrop(ModBlocks.RUBY_ORE, copperLikeOreDrops(ModBlocks.RUBY_ORE, ModItems.RAW_RUBY));// left-> what breaks, right-> what you`ll get
        //addDrop(ModBlocks.RUBY_SLAB, slabDrops(ModBlocks.RUBY_SLAB)); // same as above

        // BlockStatePropertyLootCondition.Builder builder2 = BlockStatePropertyLootCondition.builder(ModBlocks.CORN_CROP).properties(StatePredicate.Builder.create()
        //         .exactMatch(CornCropBlock.AGE, 8)); crop drop mature

        //addDrop(ModBlocks.CORN_CROP, cropDrops(ModBlocks.CORN_CROP, ModItems.CORN, ModItems.CORN_SEEDS, builder2)); crop drop normal
        addDrop(ModBlocks.EXPLORER_FRUIT_BLOCK,BlockLootTableGenerator.dropsWithShears(ModBlocks.EXPLORER_FRUIT_BLOCK, ItemEntry.builder(ModItems.EXPLORER_FRUIT)));
        addDrop(ModBlocks.MINERS_FRUIT_BLOCK);
        addDrop(ModBlocks.POTTED_EXPLORER_FRUIT_BLOCK,ModBlocks.EXPLORER_FRUIT_BLOCK);
        addDrop(ModBlocks.POTTED_MINERS_FRUIT_BLOCK,ModBlocks.MINERS_FRUIT_BLOCK);
        addDrop(ModBlocks.RELIC_CONTAINER_BLOCK,cursedRelicPool());
    }

    public LootTable.Builder copperLikeOreDrops(Block drop, Item item, float minLoot, float maxLoot) {
        var setLootCountFn = SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 5.0f));
        var enableOreFortuneFn = ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE);

        var lootEntry = ItemEntry.builder(item);

        // set drop amt
        lootEntry = lootEntry.apply(setLootCountFn);

        // add fortune logic
        lootEntry = lootEntry.apply(enableOreFortuneFn);

        // add explosion logic
        lootEntry = this.applyExplosionDecay(drop, lootEntry);

        // add silk touch logic + return loot table
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, lootEntry);
    }

    private LootTable.Builder cursedRelicPool(){
        LootPool.Builder builder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).with(Arrays.asList(ItemEntry.builder(ModBlocks.MINERS_FRUIT_BLOCK).weight(1).build(),ItemEntry.builder(ModItems.EXPLORER_FRUIT).weight(1).build()));
        return LootTable.builder().pool(builder.build());
    }
}