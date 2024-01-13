package com.bajookie.echoes_of_the_elders.world;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.world.tree.trunks.AncientTreeTrunkPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerbedBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.PredicatedStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> RELIC_CONTAINER_KEY = registerKey("relic_container");
    public static final RegistryKey<ConfiguredFeature<?, ?>> EXPLORERS_FRUIT_KEY = registerKey("explorer_fruit");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MINERS_SHROOM_KEY = registerKey("miners_shroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_FRUIT_KEY = registerKey("nether_fruit");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ANCIENT_TREE_KEY = registerKey("ancient_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SPIRITAL_GRASS_KEY = registerKey("spirital_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LESS_CHERRY_KEY = registerKey("less_cherry");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CLAY_DISK_KEY = registerKey("river_clay_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SAND_DISK_KEY = registerKey("river_sand_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OCEAN_SAND_KEY = registerKey("ocean_sand_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRAVEL_DISK_KEY = registerKey("river_gravel_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LILY_PAD_KEY = registerKey("river_lily");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        makeWaterConfigFeatures(context);
        makeGroundConfigFeatures(context);
        makeVegetationConfigFeatures(context);

    }

    public static void makeWaterConfigFeatures(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, CLAY_DISK_KEY, Feature.DISK, new DiskFeatureConfig(PredicatedStateProvider.of(Blocks.CLAY), BlockPredicate.matchingBlocks(List.of(Blocks.DIRT, Blocks.GRAVEL,Blocks.CLAY)), UniformIntProvider.create(2, 3), 1));
        register(context, SAND_DISK_KEY, Feature.DISK, new DiskFeatureConfig(PredicatedStateProvider.of(Blocks.SAND), BlockPredicate.matchingBlocks(List.of(Blocks.DIRT, Blocks.GRAVEL)), UniformIntProvider.create(6, 8), 1));
        register(context, GRAVEL_DISK_KEY, Feature.DISK, new DiskFeatureConfig(PredicatedStateProvider.of(Blocks.GRAVEL), BlockPredicate.matchingBlocks(List.of(Blocks.DIRT, Blocks.GRAVEL)), UniformIntProvider.create(6, 8), 1));
        register(context, OCEAN_SAND_KEY, Feature.REPLACE_SINGLE_BLOCK, new EmeraldOreFeatureConfig(Blocks.GRAVEL.getDefaultState(),Blocks.SAND.getDefaultState()));

    }

    public static void makeGroundConfigFeatures(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest deepslateReplaceAble = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreFeatureConfig.Target> overworldRelicContainer = List.of(OreFeatureConfig.createTarget(deepslateReplaceAble, ModBlocks.ARTIFACT_VAULT.getDefaultState()));
        register(context, RELIC_CONTAINER_KEY, Feature.ORE, new OreFeatureConfig(overworldRelicContainer, 12));

    }

    public static void makeVegetationConfigFeatures(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, LESS_CHERRY_KEY, Feature.FLOWER, createRandomCherryPatchFeatureConfig(18));
        register(context, SPIRITAL_GRASS_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(BlockStateProvider.of(ModBlocks.SPIRITAL_GRASS), 32));
        register(context, ANCIENT_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(Blocks.CHERRY_LOG),
                new AncientTreeTrunkPlacer(20, 5, 6),
                BlockStateProvider.of(Blocks.CHERRY_LEAVES),
                new JungleFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(2), 1),
                new TwoLayersFeatureSize(1, 0, 2)).build());
        register(context, NETHER_FRUIT_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(1, 1, 1, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.NETHER_FRUIT_BLOCK)))));
        register(context, MINERS_SHROOM_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(1, 1, 1, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.MINERS_FRUIT_BLOCK)))));
        register(context, EXPLORERS_FRUIT_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(1, 1, 1, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.EXPLORER_FRUIT_BLOCK)))));
        register(context,LILY_PAD_KEY,Feature.RANDOM_PATCH,new RandomPatchFeatureConfig(4, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.LILY_PAD)))));

    }


    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(EOTE.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }

    private static RandomPatchFeatureConfig createRandomCherryPatchFeatureConfig(int tries) {
        DataPool.Builder<BlockState> builder = DataPool.builder();
        for (int i = 1; i <= 4; ++i) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                builder.add((BlockState) ((BlockState) Blocks.PINK_PETALS.getDefaultState().with(FlowerbedBlock.FLOWER_AMOUNT, i)).with(FlowerbedBlock.FACING, direction), 1);
            }
        }
        return new RandomPatchFeatureConfig(tries, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(builder))));
    }
}
