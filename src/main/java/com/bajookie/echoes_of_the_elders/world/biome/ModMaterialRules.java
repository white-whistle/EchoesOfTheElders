package com.bajookie.echoes_of_the_elders.world.biome;

import net.fabricmc.fabric.mixin.biome.ChunkNoiseSamplerMixin;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class ModMaterialRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule SAND = makeStateRule(Blocks.SAND);
    private static final MaterialRules.MaterialRule GRAVEL = makeStateRule(Blocks.GRAVEL);


    public static MaterialRules.MaterialRule makeRules() {
        return MaterialRules.sequence(makeRiverRules(),makeOceanRules(),makeDeepOceanRules());
    }

    private static MaterialRules.MaterialRule makeRiverRules(){
        return MaterialRules.condition(MaterialRules.biome(ModBiomes.LOST_RIVER_BIOME),
            MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,SAND));
    }
    private static MaterialRules.MaterialRule makeOceanRules(){
        return MaterialRules.condition(MaterialRules.biome(ModBiomes.LOST_OCEAN_BIOME),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,SAND));
    }
    private static MaterialRules.MaterialRule makeDeepOceanRules(){
        return MaterialRules.condition(MaterialRules.biome(ModBiomes.DEEP_LOST_OCEAN_BIOME),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING,SAND));
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
