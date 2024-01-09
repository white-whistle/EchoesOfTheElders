package com.bajookie.echoes_of_the_elders.world;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.EOTE;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.CherryFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.CherryTrunkPlacer;

import java.util.List;
import java.util.Random;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> RELIC_CONTAINER_KEY = registerKey("relic_container");
    public static final RegistryKey<ConfiguredFeature<?,?>> EXPLORERS_FRUIT_KEY = registerKey("explorer_fruit");
    public static final RegistryKey<ConfiguredFeature<?,?>> MINERS_SHROOM_KEY = registerKey("miners_shroom");
    public static final RegistryKey<ConfiguredFeature<?,?>> NETHER_FRUIT_KEY = registerKey("nether_fruit");
    public static final RegistryKey<ConfiguredFeature<?,?>> ANCIENT_TREE_KEY = registerKey("ancient_tree");

    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context){
        RuleTest deepslateReplaceAble = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        //new BlockMatchRuleTest() use for endstone

        List<OreFeatureConfig.Target> overworldRelicContainer = List.of(OreFeatureConfig.createTarget(deepslateReplaceAble, ModBlocks.RELIC_CONTAINER_BLOCK.getDefaultState()));
        register(context,RELIC_CONTAINER_KEY,Feature.ORE,new OreFeatureConfig(overworldRelicContainer,12));
        register(context,EXPLORERS_FRUIT_KEY,Feature.FLOWER,new RandomPatchFeatureConfig(1,1,1,PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.EXPLORER_FRUIT_BLOCK)))));
        register(context,MINERS_SHROOM_KEY,Feature.FLOWER,new RandomPatchFeatureConfig(1,1,1,PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.MINERS_FRUIT_BLOCK)))));
        register(context,NETHER_FRUIT_KEY,Feature.FLOWER,new RandomPatchFeatureConfig(1,1,1,PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.NETHER_FRUIT_BLOCK)))));
        register(context,ANCIENT_TREE_KEY,Feature.TREE,new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.ANCIENT_TREE_LOG),
                new CherryTrunkPlacer(7,
                        1,
                        0,
                        UniformIntProvider.create(2,3),
                        UniformIntProvider.create(2, 4),
                        UniformIntProvider.create(-4, -3),
                        UniformIntProvider.create(-1, 0)),
                BlockStateProvider.of(ModBlocks.ANCIENT_TREE_LEAVES),
                new CherryFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(0), ConstantIntProvider.create(5), 0.25f, 0.5f, 0.16666667f, 0.33333334f),
                new TwoLayersFeatureSize(1,0,2)).build());
    }
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(EOTE.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
