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
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> RELIC_CONTAINER_KEY = registerKey("relic_container");
    public static final RegistryKey<ConfiguredFeature<?,?>> EXPLORERS_FRUIT_KEY = registerKey("explorer_fruit");
    public static final RegistryKey<ConfiguredFeature<?,?>> MINERS_SHROOM_KEY = registerKey("miners_shroom");

    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context){
        RuleTest deepslateReplaceAble = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        //new BlockMatchRuleTest() use for endstone

        List<OreFeatureConfig.Target> overworldRelicContainer = List.of(OreFeatureConfig.createTarget(deepslateReplaceAble, ModBlocks.RELIC_CONTAINER_BLOCK.getDefaultState()));
        register(context,RELIC_CONTAINER_KEY,Feature.ORE,new OreFeatureConfig(overworldRelicContainer,12));
        register(context,EXPLORERS_FRUIT_KEY,Feature.FLOWER,new RandomPatchFeatureConfig(32,6,2,PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.EXPLORER_FRUIT_BLOCK)))));
        register(context,MINERS_SHROOM_KEY,Feature.FLOWER,new RandomPatchFeatureConfig(32,6,2,PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.MINERS_FRUIT_BLOCK)))));
    }
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(EOTE.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
