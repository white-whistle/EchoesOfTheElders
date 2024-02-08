package com.bajookie.echoes_of_the_elders.world.biome;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.world.ModPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.EndPlacedFeatures;
import net.minecraft.world.gen.feature.OceanPlacedFeatures;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModBiomes {
    public static final RegistryKey<Biome> LOST_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(MOD_ID, "lost_biome"));
    public static final RegistryKey<Biome> LOST_OCEAN_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(MOD_ID, "lost_ocean_biome"));
    public static final RegistryKey<Biome> DEEP_LOST_OCEAN_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(MOD_ID, "deep_lost_ocean_biome"));
    public static final RegistryKey<Biome> LOST_RIVER_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(MOD_ID, "lost_river_biome"));
    public static final RegistryKey<Biome> LOST_BEACH_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(MOD_ID, "lost_beach_biome"));


    public static void bootstrap(Registerable<Biome> ctx) {
        ctx.register(LOST_BIOME, lostBiome(ctx));
        ctx.register(LOST_OCEAN_BIOME, lostOceanBiome(ctx));
        ctx.register(DEEP_LOST_OCEAN_BIOME, deepLostOceanBiome(ctx));
        ctx.register(LOST_RIVER_BIOME, lostRiverBiome(ctx));
        ctx.register(LOST_BEACH_BIOME, lostBeachBiome(ctx));
    }


    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
        DefaultBiomeFeatures.addDefaultOres(builder);
        DefaultBiomeFeatures.addEmeraldOre(builder);
    }


    public static Biome lostBiome(Registerable<Biome> ctx) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.SPIRIT_ENTITY_KEY, 1, 1, 1));
        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(
                ctx.getRegistryLookup(RegistryKeys.PLACED_FEATURE), ctx.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
        );
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.LESS_CHERRY_FLOWER_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SAKURA_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ANCIENT_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SPIRITAL_GRASS_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.EXPLORER_FRUIT_PLACED_KEY);

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
                        .grassColor(0xB6A7B6)
                        .foliageColor(0xf2f2f2)
                        .fogColor(0x8e948f)
                        .loopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .build())
                .build();
    }
    public static Biome lostOceanBiome(Registerable<Biome> ctx) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 12));
        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(
                ctx.getRegistryLookup(RegistryKeys.PLACED_FEATURE), ctx.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
        );
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.feature(GenerationStep.Feature.RAW_GENERATION,ModPlacedFeatures.OCEAN_SAND_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.SEAGRASS_WARM);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.KELP_WARM);

        //Generation steps must follow  GenerationStep!!!!

        return new Biome.Builder()
                .downfall(0.4f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects(new BiomeEffects.Builder()
                        .waterColor(0x2d88c2)
                        .waterFogColor(267827)
                        .skyColor(0xbdd1f2)
                        .grassColor(0xB6A7B6)
                        .foliageColor(0xf2f2f2)
                        .fogColor(0x8e948f)
                        .loopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .build())
                .build();
    }
    public static Biome deepLostOceanBiome(Registerable<Biome> ctx) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.GLOW_SQUID, 6, 8, 12));
        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(
                ctx.getRegistryLookup(RegistryKeys.PLACED_FEATURE), ctx.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
        );
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.SEAGRASS_DEEP_WARM);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.KELP_WARM);

        //Generation steps must follow  GenerationStep!!!!

        return new Biome.Builder()
                .downfall(0.4f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects(new BiomeEffects.Builder()
                        .waterColor(0x0f5e91)
                        .waterFogColor(267827)
                        .skyColor(0xbdd1f2)
                        .grassColor(0xB6A7B6)
                        .foliageColor(0xf2f2f2)
                        .fogColor(0x8e948f)
                        .loopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .build())
                .build();
    }
    public static Biome lostRiverBiome(Registerable<Biome> ctx) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 25, 4, 8));
        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(
                ctx.getRegistryLookup(RegistryKeys.PLACED_FEATURE), ctx.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
        );
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.feature(GenerationStep.Feature.UNDERGROUND_ORES,ModPlacedFeatures.CLAY_DISK_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.UNDERGROUND_ORES,ModPlacedFeatures.SAND_DISK_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.SEAGRASS_RIVER);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION,ModPlacedFeatures.LILY_PAD_PLACED_KEY);

        //Generation steps must follow  GenerationStep!!!!
        return new Biome.Builder()
                .downfall(0.4f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects(new BiomeEffects.Builder()
                        .waterColor(0x70d4bb)
                        .waterFogColor(0x3280d9)
                        .skyColor(0xbdd1f2)
                        .grassColor(0xB6A7B6)
                        .foliageColor(0xf2f2f2)
                        .fogColor(0x8e948f)
                        .loopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .build())
                .build();
    }
    public static Biome lostBeachBiome(Registerable<Biome> ctx) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TURTLE, 5, 2, 4));
        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(
                ctx.getRegistryLookup(RegistryKeys.PLACED_FEATURE), ctx.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
        );
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.SEAGRASS_RIVER);

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
                        .grassColor(0xB6A7B6)
                        .foliageColor(0xf2f2f2)
                        .fogColor(0x8e948f)
                        .loopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .build())
                .build();
    }
}
