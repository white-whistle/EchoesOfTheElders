package com.bajookie.biotech.world;

import com.bajookie.biotech.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> RELIC_CONTAINER_KEY = registerKey("relic_container");

    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context){
        RuleTest deepslateReplaceAble = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        //new BlockMatchRuleTest() use for endstone

        List<OreFeatureConfig.Target> overworldRelicContainer = List.of(OreFeatureConfig.createTarget(deepslateReplaceAble, ModBlocks.RELIC_CONTAINER_BLOCK.getDefaultState()));
        register(context,RELIC_CONTAINER_KEY,Feature.ORE,new OreFeatureConfig(overworldRelicContainer,12));
    }
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
