package com.bajookie.echoes_of_the_elders.world.gen;

import com.bajookie.echoes_of_the_elders.world.ModUndergroundGeneration;

public class ModWorldGeneration{

    public static void generateModWorldGen(){
        ModOreGeneration.generateOres();
        ModUndergroundGeneration.generateUndergroundFeatures();
        ModVegetationGeneration.generateFlowers();
        ModMobSpawns.addSpawns();
        ModTreeGeneration.generateTrees(); // empty!
    }


}
