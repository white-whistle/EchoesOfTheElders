import { exit } from "process";
import { promises as fs } from "fs";

const MOD_ID = "echoes_of_the_elders"
const MC_VERSION = "1.20.2"

const settings = {
    output: `./src/main/resources/data/${MOD_ID}/dimension/defense_dim.json`,

    ignoreBiomes: ["echoes_of_the_elders:lost_biome","echoes_of_the_elders:lost_river_biome","echoes_of_the_elders:lost_ocean_biome","echoes_of_the_elders:deep_lost_ocean_biome","echoes_of_the_elders:lost_beach_biome"
        ],

    mapPresetBiomeToDimBiome(presetBiome: string) {
        if (settings.ignoreBiomes.includes((presetBiome))) return presetBiome;

        switch (presetBiome) {
            // replace
            case 'minecraft:deep_frozen_ocean':
            case "minecraft:deep_ocean":
            case "minecraft:deep_lukewarm_ocean":
            case "minecraft:deep_cold_ocean":
                return `${MOD_ID}:deep_lost_ocean_biome`;
            case 'minecraft:frozen_ocean':
            case "minecraft:lukewarm_ocean":
            case "minecraft:warm_ocean":
            case "minecraft:ocean":
            case "minecraft:cold_ocean":
                return `${MOD_ID}:lost_ocean_biome`;

            case 'minecraft:frozen_river':
            case 'minecraft:river':
                return `${MOD_ID}:lost_river_biome`
            case 'minecraft:beach':
            case 'minecraft:snowy_beach':
                return `${MOD_ID}:lost_beach_biome`

            // default
            default:
                return `${MOD_ID}:lost_biome`
        }
    }
}

const overworldPresetUrl = `https://raw.githubusercontent.com/misode/mcmeta/${MC_VERSION}-data/data/minecraft/dimension/overworld.json`

type Biome = {
    biome: string
}

type Dimension = {
    type: string,
    generator: {
        type: string,
        settings: string,
        biome_source: {
            type: string,
            biomes: Biome[]
        }
    },
};

async function run() {
    console.log("[🔄] Getting overworld preset...")
    const overworldPreset: Dimension = await fetch(overworldPresetUrl).then(data => data.json());
    console.log(`[✅] Preset found for mc - ${MC_VERSION}`)

    overworldPreset.type = `${MOD_ID}:defense_dim_type`

    const stats = { replaced: 0 };
    overworldPreset.generator.biome_source.biomes.forEach(biomeDefinition => {
        const newBiome = settings.mapPresetBiomeToDimBiome(biomeDefinition.biome);

        if (newBiome !== biomeDefinition.biome) {
            stats.replaced ++;
            biomeDefinition.biome = settings.mapPresetBiomeToDimBiome(biomeDefinition.biome);
        }
    })
    console.log(`[🔄] Replaced [${stats.replaced}/${overworldPreset.generator.biome_source.biomes.length}] entries`)

    console.log("[🔄] Generating dimension file...")
    await fs.writeFile(settings.output, JSON.stringify(overworldPreset, null, 2));

    console.log(`[✅] Generated dimension - ${settings.output}`);
}

run().catch(err => {
    console.error(err);
    exit(1)
});
