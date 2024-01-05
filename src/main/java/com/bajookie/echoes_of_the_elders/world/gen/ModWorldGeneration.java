package com.bajookie.echoes_of_the_elders.world.gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModWorldGeneration{

    public static void generateModWorldGen(){
        ModOreGeneration.generateOres();
        ModFlowerGeneration.generateFlowers();
        ModMobSpawns.addSpawns();
        ModTreeGeneration.generateTrees(); // empty!
    }


}
