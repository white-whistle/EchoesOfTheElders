package com.bajookie.echoes_of_the_elders.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class ModUndergroundGeneration {
    public static void generateUndergroundFeatures(){
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.MINERS_SHROOM_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.NETHER_FRUIT_PLACED_KEY);
    }
}
