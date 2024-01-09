package com.bajookie.echoes_of_the_elders.world.biome;

import com.bajookie.echoes_of_the_elders.world.ModPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModBiomes {
    public static final RegistryKey<Biome> LOST_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(MOD_ID,"lost_biome"));

    public static void bootstrap(Registerable<Biome> ctx){
        ctx.register(LOST_BIOME,lostBiome(ctx));
    }


    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder){
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    public static Biome lostBiome(Registerable<Biome> ctx){
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE,new SpawnSettings.SpawnEntry(EntityType.MOOSHROOM,4,6,10));
        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(
                ctx.getRegistryLookup(RegistryKeys.PLACED_FEATURE),ctx.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
        );
        globalOverworldGeneration(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addEmeraldOre(biomeBuilder);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SAKURA_TREE_PLACED_KEY);
        DefaultBiomeFeatures.addDefaultMushrooms(biomeBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder);
        DefaultBiomeFeatures.addJungleGrass(biomeBuilder);
        DefaultBiomeFeatures.addKelp(biomeBuilder);

        //Generation steps must follow  GenerationStep!!!!

        return new Biome.Builder()
                .downfall(0.4f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects(new BiomeEffects.Builder()
                        .waterColor(0x61c2ff)
                        .waterFogColor(0x3280d9)
                        .skyColor(0xbdd1f2)
                        .grassColor(0xf2f2f2)
                        .foliageColor(0xf2f2f2)
                        .fogColor(0x8e948f).build())
                .build();
    }
}
