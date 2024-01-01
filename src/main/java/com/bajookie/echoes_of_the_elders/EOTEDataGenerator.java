package com.bajookie.echoes_of_the_elders;

import com.bajookie.echoes_of_the_elders.datagen.ModModelProvider;
import com.bajookie.echoes_of_the_elders.datagen.ModWorldGenerator;
import com.bajookie.echoes_of_the_elders.util.*;
import com.bajookie.echoes_of_the_elders.world.ModConfiguredFeatures;
import com.bajookie.echoes_of_the_elders.world.ModPlacedFeatures;
import com.bajookie.echoes_of_the_elders.world.biome.ModBiomes;
import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class EOTEDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModWorldGenerator::new);
    }

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, ModDimensions::bootstrapType);
        registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
	}
}
