package com.bajookie.biotech.item;

import com.bajookie.biotech.BioTech;
import com.bajookie.biotech.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.bajookie.biotech.BioTech.MOD_ID;

@SuppressWarnings("unused")
public class ModItemGroups {
    public static final ItemGroup MOD_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "mod_item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.bio")).icon(() -> new ItemStack(ModBlocks.MINERS_FRUIT_BLOCK)).entries((displayContext, entries) -> {
                entries.add(ModItems.EXPLORER_FRUIT);
                // blocks
                entries.add(ModBlocks.BEAR_TRAP_BLOCK);
                // flowers
                entries.add(ModBlocks.EXPLORER_FRUIT_BLOCK);
                entries.add(ModBlocks.MINERS_FRUIT_BLOCK);
                entries.add(ModBlocks.NETHER_FRUIT_BLOCK);
                entries.add(ModBlocks.RELIC_CONTAINER_BLOCK);
            }).build());

    public static final ItemGroup MOD_ARTIFACT_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "mod_artifact_group"),
            FabricItemGroup.builder().displayName(Text.translatable("artifact-group.bio")).icon(() -> new ItemStack(ModItems.VITALITY_PUMP)).entries((displayContext, entries) -> {
                entries.add(ModItems.ANCIENT_STONE_SWORD);
                entries.add(ModItems.RADIANT_LOTUS);
                entries.add(ModItems.SHINY_ANCIENT_STONE_SWORD);
                entries.add(ModItems.VITALITY_PUMP);
                entries.add(ModItems.PORTAL_RING);
                entries.add(ModItems.MIDAS_HAMMER);
                entries.add(ModItems.GALE_CORE);
                entries.add(ModItems.FIRE_SNAP);
                entries.add(ModItems.DAGON);
                // disabled until we think this one out
                // entries.add(ModItems.SILENT_FIRE);
            }).build());

    public static void registerGroups() {
        BioTech.LOGGER.info("Registering Item Groups for ---> " + MOD_ID);
    }
}
