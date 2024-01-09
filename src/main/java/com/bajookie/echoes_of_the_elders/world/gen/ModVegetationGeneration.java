package com.bajookie.echoes_of_the_elders.world.gen;

import com.bajookie.echoes_of_the_elders.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class ModVegetationGeneration {
    public static void generateFlowers(){
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.EXPLORER_FRUIT_PLACED_KEY);
    }
}
