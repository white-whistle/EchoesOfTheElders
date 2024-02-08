package com.bajookie.echoes_of_the_elders.world;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RELIC_CONTAINER_PLACED_KEY = registerKey("relic_container_placed");
    public static final RegistryKey<PlacedFeature> EXPLORER_FRUIT_PLACED_KEY = registerKey("explorer_fruit");
    public static final RegistryKey<PlacedFeature> MINERS_SHROOM_PLACED_KEY = registerKey("miners_shroom");
    public static final RegistryKey<PlacedFeature> ANCIENT_TREE_PLACED_KEY = registerKey("ancient_tree_place");
    public static final RegistryKey<PlacedFeature> SAKURA_TREE_PLACED_KEY = registerKey("sakura_tree_place");
    public static final RegistryKey<PlacedFeature> SPIRITAL_GRASS_PLACED_KEY = registerKey("spirital_grass_place");
    public static final RegistryKey<PlacedFeature> NETHER_FRUIT_PLACED_KEY = registerKey("nether_fruit_place");
    public static final RegistryKey<PlacedFeature> LESS_CHERRY_FLOWER_PLACED_KEY = registerKey("less_cherry_place");
    public static final RegistryKey<PlacedFeature> CLAY_DISK_PLACED_KEY = registerKey("clay_disk_place");
    public static final RegistryKey<PlacedFeature> SAND_DISK_PLACED_KEY = registerKey("sand_disk_place");
    public static final RegistryKey<PlacedFeature> GRAVEL_DISK_PLACED_KEY = registerKey("gravel_disk_place");
    public static final RegistryKey<PlacedFeature> OCEAN_SAND_PLACED_KEY = registerKey("ocean_sand_place");
    public static final RegistryKey<PlacedFeature> LILY_PAD_PLACED_KEY = registerKey("lily_pad_place");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        registerGroundPlacedFeatures(context, configuredFeatureRegistryEntryLookup);
        registerVegetationPlacedFeatures(context, configuredFeatureRegistryEntryLookup);
        registerWaterPlacedFeatures(context, configuredFeatureRegistryEntryLookup);}

    public static void registerWaterPlacedFeatures(Registerable<PlacedFeature> context, RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryEntryLookup) {
        register(context, CLAY_DISK_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.CLAY_DISK_KEY),
                List.of(SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.matchingFluids(Fluids.WATER)), BiomePlacementModifier.of()));
        register(context, SAND_DISK_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SAND_DISK_KEY),
                List.of(SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.matchingFluids(Fluids.WATER)), BiomePlacementModifier.of()));
        register(context, GRAVEL_DISK_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.GRAVEL_DISK_KEY),
                List.of(SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.matchingFluids(Fluids.WATER)), BiomePlacementModifier.of()));
        register(context, OCEAN_SAND_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.OCEAN_SAND_KEY),
                List.of(SurfaceWaterDepthFilterPlacementModifier.of(30), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.matchingFluids(Fluids.WATER)), BiomePlacementModifier.of()));
    }

    public static void registerGroundPlacedFeatures(Registerable<PlacedFeature> context, RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryEntryLookup) {
        register(context, RELIC_CONTAINER_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.RELIC_CONTAINER_KEY),
                ModOrePlacement.modifiersWithCount(12, //veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(90)//where to place
                        )));
    }

    public static void registerVegetationPlacedFeatures(Registerable<PlacedFeature> context, RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryEntryLookup) {
        register(context, LESS_CHERRY_FLOWER_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LESS_CHERRY_KEY),
                List.of(NoiseThresholdCountPlacementModifier.of(-0.8, 5, 10), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        register(context, SPIRITAL_GRASS_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SPIRITAL_GRASS_KEY),
                List.of(NoiseThresholdCountPlacementModifier.of(-0.8, 5, 10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        register(context, SAKURA_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(TreeConfiguredFeatures.CHERRY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.1f, 2),
                        Blocks.CHERRY_SAPLING));
        register(context, ANCIENT_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ANCIENT_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.01f, 1),
                        ModBlocks.ANCIENT_TREE_SAPLING));
        register(context, MINERS_SHROOM_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MINERS_SHROOM_KEY),
                List.of(CountPlacementModifier.of(UniformIntProvider.create(10, 15)), PlacedFeatures.BOTTOM_TO_120_RANGE, SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomePlacementModifier.of()));
        register(context, EXPLORER_FRUIT_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.EXPLORERS_FRUIT_KEY),
                List.of(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        register(context, NETHER_FRUIT_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHER_FRUIT_KEY),
                List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of()));
        register(context, LILY_PAD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LILY_PAD_KEY),
                List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(4)));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(EOTE.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
