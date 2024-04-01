package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.*;

public class ModLootTablesModifiers {
    private static final Identifier ABANDON_ID = new Identifier("minecraft", "chests/abandoned_mineshaft");

    private static final Collection<LootPoolEntry> ITEMS_TO_DROP = List.of(
            ItemEntry.builder(ModItems.GALE_QUIVER).weight(1).build(),
            ItemEntry.builder(ModItems.DOOMSTICK_ITEM).weight(1).build(),
            ItemEntry.builder(ModItems.PORTAL_RING).weight(1).build(),
            ItemEntry.builder(ModItems.MIDAS_HAMMER).weight(1).build(),
            ItemEntry.builder(ModItems.SCORCHERS_MITTS).weight(1).build(),
            ItemEntry.builder(ModItems.RADIANT_LOTUS).weight(1).build()
    );

    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (ABANDON_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.8f))// chance
                        .with(ITEMS_TO_DROP) // what item to drop
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 1f)).build()); // how much to drop
                tableBuilder.pool(poolBuilder.build());
            }
        }));
    }
}
