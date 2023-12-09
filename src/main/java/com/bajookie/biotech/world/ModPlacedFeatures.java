package com.bajookie.biotech.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RELIC_CONTAINER_PLACED_KEY = registerKey("relic_container_placed");

    public static void bootstrap(Registerable<PlacedFeature> context){
        var configeredFeatureRegistryEnteryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(context,RELIC_CONTAINER_PLACED_KEY,configeredFeatureRegistryEnteryLookup.getOrThrow(ModConfiguredFeatures.RELIC_CONTAINER_KEY),
                ModOrePlacement.modifiersWithCount(12, //veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80),YOffset.fixed(90)//where to place
                        )));
    }
    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
