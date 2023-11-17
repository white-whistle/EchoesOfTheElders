package com.bajookie.biotech.util;

import com.bajookie.biotech.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTablesModifiers {
    private static final Identifier BIRCH_ID = new Identifier("minecraft","blocks/birch_leaves");
    private static final Identifier AZALEA_LEAVES = new Identifier("minecraft","blocks/azalea_leaves");
    public static void modifyLootTables(){
        // LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
        //     if (BIRCH_ID.equals(id)){
        //         LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
        //                 .conditionally(RandomChanceLootCondition.builder(0.01f))// chance
        //                 .with(ItemEntry.builder(ModItems.LEMON)) // what item to drop
        //                 .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f,1f)).build()); // how much to drop
        //         tableBuilder.pool(poolBuilder.build());
        //     }
        //     if (AZALEA_LEAVES.equals(id)){
        //         LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
        //                 .conditionally(RandomChanceLootCondition.builder(0.01f))// chance
        //                 .with(ItemEntry.builder(ModItems.ORANGE)) // what item to drop
        //                 .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f,1f)).build()); // how much to drop
        //         tableBuilder.pool(poolBuilder.build());
        //     }
        // }));
    }
}
