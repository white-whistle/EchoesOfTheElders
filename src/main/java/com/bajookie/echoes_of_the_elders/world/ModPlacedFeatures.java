package com.bajookie.echoes_of_the_elders.world;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RELIC_CONTAINER_PLACED_KEY = registerKey("relic_container_placed");
    public static final RegistryKey<PlacedFeature> EXPLORER_FRUIT_PLACED_KEY = registerKey("explorer_fruit");
    public static final RegistryKey<PlacedFeature> MINERS_SHROOM_PLACED_KEY = registerKey("miners_shroom");
    public static final RegistryKey<PlacedFeature> ANCIENT_TREE_PLACED_KEY = registerKey("ancient_tree_place");
    public static final RegistryKey<PlacedFeature> SAKURA_TREE_PLACED_KEY = registerKey("sakura_tree_place");

    public static void bootstrap(Registerable<PlacedFeature> context){
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(context,RELIC_CONTAINER_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.RELIC_CONTAINER_KEY),
                ModOrePlacement.modifiersWithCount(12, //veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80),YOffset.fixed(90)//where to place
                        )));
        register(context,EXPLORER_FRUIT_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.EXPLORERS_FRUIT_KEY),
                List.of(RarityFilterPlacementModifier.of(8) ,SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));

        register(context,MINERS_SHROOM_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MINERS_SHROOM_KEY),
                List.of(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        register(context,ANCIENT_TREE_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ANCIENT_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(2,0.1f,2),
                        ModBlocks.ANCIENT_TREE_SAPLING));
        register(context,SAKURA_TREE_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(TreeConfiguredFeatures.CHERRY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(2,0.1f,2),
                        Blocks.CHERRY_SAPLING));
    }
    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(EOTE.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
