package com.bajookie.echoes_of_the_elders.item;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@SuppressWarnings("unused")
public class ModItemGroups {
    public static final ItemGroup MOD_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "mod_item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.echoes_of_the_elders")).icon(() -> new ItemStack(ModBlocks.MINERS_FRUIT_BLOCK)).entries((displayContext, entries) -> {
                entries.add(ModItems.EXPLORER_FRUIT);
                // blocks
                // entries.add(ModBlocks.BEAR_TRAP_BLOCK);
                // flowers
                entries.add(ModBlocks.EXPLORER_FRUIT_BLOCK);
                entries.add(ModBlocks.MINERS_FRUIT_BLOCK);
                entries.add(ModBlocks.NETHER_FRUIT_BLOCK);
                entries.add(ModItems.SPIRIT_SPAWN_EGG);
                entries.add(ModItems.ELDERMAN_SPAWN_EGG);
                entries.add(ModBlocks.ARTIFACT_VAULT);
                // entries.add(ModBlocks.SUN_RUNE_BLOCK);
                // entries.add(ModBlocks.CHISELED_MOSSY_STONE);
                // entries.add(ModBlocks.ELDER_LILY_FLOWER);
                // trees
                // entries.add(ModBlocks.ANCIENT_TREE_LEAVES);
                // entries.add(ModBlocks.ANCIENT_TREE_LOG);
                // entries.add(ModBlocks.ANCIENT_TREE_WOOD);
                // entries.add(ModBlocks.ANCIENT_TREE_PLANKS);
                // entries.add(ModBlocks.STRIPPED_ANCIENT_TREE_LOG);
                // entries.add(ModBlocks.STRIPPED_ANCIENT_TREE_WOOD);
                entries.add(ModBlocks.ANCIENT_TREE_SAPLING);
                entries.add(ModItems.OLD_KEY);
                entries.add(ModItems.CORRUPTED_KEY);
                entries.add(ModBlocks.SPIRITAL_GRASS);
                entries.add(ModItems.RAID_DEBUG_ITEM);
                // entries.add(ModBlocks.SPIRIT_PETALS);
                entries.add(ModBlocks.SPIRIT_PETALS);
                entries.add(ModBlocks.WOOL_TENT_DOOR);
                entries.add(ModBlocks.COAL_CRYSTAL);
                entries.add(ModBlocks.IRON_CRYSTAL);
                entries.add(ModBlocks.GOLD_CRYSTAL);
                entries.add(ModBlocks.DIAMOND_CRYSTAL);
                entries.add(ModBlocks.EMERALD_CRYSTAL);
                entries.add(ModBlocks.ORE_CREATOR_BLOCK);
                // entries.add(ModBlocks.SPIRIT_PETALS);
                // entries.add(ModBlocks.SPIRIT_PETALS);
            }).build());

    public static final ItemGroup MOD_ARTIFACT_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "mod_artifact_group"),
            FabricItemGroup.builder().displayName(Text.translatable("artifact-group.echoes_of_the_elders")).icon(() -> new ItemStack(ModItems.VITALITY_PUMP)).entries((displayContext, entries) -> {
                // entries.add(ModItems.ANCIENT_STONE_SWORD);
                // entries.add(ModItems.SHINY_ANCIENT_STONE_SWORD);
                entries.add(ModItems.RADIANT_LOTUS);
                entries.add(ModItems.VITALITY_PUMP);
                entries.add(ModItems.PORTAL_RING);
                entries.add(ModItems.MIDAS_HAMMER);
                entries.add(ModItems.GALE_CORE);
                entries.add(ModItems.SCORCHERS_MITTS);
                entries.add(ModItems.DOOMSTICK_ITEM);
                entries.add(ModItems.POTION_MIRAGE);
                entries.add(ModItems.WITHER_SCALES_ITEM);
                entries.add(ModItems.QUICKENING_BAND);
                // entries.add(ModItems.SECOND_SUN_ITEM);
                entries.add(ModItems.CHAIN_LIGHTNING_ITEM);
                entries.add(ModItems.REALITY_PICK);
                entries.add(ModItems.GODSLAYER);
                entries.add(ModItems.GUNHEELS);
                entries.add(ModItems.ELDER_PRISM);
                // entries.add(ModItems.STAT_FRUIT_HP);
                entries.add(ModItems.TIME_TOKEN);
                entries.add(ModItems.WTF_RELIC);
                entries.add(ModItems.TESTING_TOOL);
                // disabled until we think this one out
                // entries.add(ModItems.SILENT_FIRE);
            }).build());

    public static void registerGroups() {
        EOTE.LOGGER.info("Registering Item Groups for ---> " + MOD_ID);
    }
}
