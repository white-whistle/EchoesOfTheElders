package com.bajookie.echoes_of_the_elders.world.biome;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.mixin.biome.ChunkNoiseSamplerMixin;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModMaterialRules {
    private static final MaterialRules.MaterialRule GOLDEN = makeStateRule(Blocks.GOLD_BLOCK);
    private static final MaterialRules.MaterialRule SAND = makeStateRule(Blocks.SAND);
    private static final MaterialRules.MaterialRule DIAMONDY = makeStateRule(Blocks.DIAMOND_BLOCK);

    public static MaterialRules.MaterialRule makeRules() {
        return MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(ModBiomes.LOST_BIOME)
                        ,MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,GOLDEN)),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING,DIAMONDY));
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
