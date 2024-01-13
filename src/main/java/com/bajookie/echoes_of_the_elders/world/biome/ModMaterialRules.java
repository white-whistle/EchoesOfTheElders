package com.bajookie.echoes_of_the_elders.world.biome;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.mixin.biome.ChunkNoiseSamplerMixin;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModMaterialRules {
    private static final MaterialRules.MaterialRule GOLD_BLOCK = makeStateRule(Blocks.GOLD_BLOCK);
    private static final MaterialRules.MaterialRule SAND = makeStateRule(Blocks.SAND);
    private static final MaterialRules.MaterialRule SANDSTONE = makeStateRule(Blocks.SANDSTONE);
    private static final MaterialRules.MaterialRule STONE = makeStateRule(Blocks.STONE);
    private static final MaterialRules.MaterialRule GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final MaterialRules.MaterialRule DIAMOND_BLOCK = makeStateRule(Blocks.DIAMOND_BLOCK);

    public static MaterialRules.MaterialRule makeRules() {
        return MaterialRules.sequence(makeOceanRules());
    }

    private static MaterialRules.MaterialRule makeOceanRules() {
        return MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.biome(ModBiomes.LOST_OCEAN_BIOME),
                        MaterialRules.condition(MaterialRules.not(MaterialRules.water(0,0)),MaterialRules.condition(MaterialRules.stoneDepth(0,false,VerticalSurfaceType.FLOOR),SAND))));
    }

    private static MaterialRules.MaterialRule makeDeepOceanRules() {
        return MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(ModBiomes.DEEP_LOST_OCEAN_BIOME),
                MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, GRAVEL), STONE)));
    }

    private static MaterialRules.MaterialRule makeRiverRules() {
        return null;
    }

    private static MaterialRules.MaterialRule makeBeachRules() {
        return null;
    }

    private static MaterialRules.MaterialRule makeLandRules() {
        return null;
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
