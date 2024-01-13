package com.bajookie.echoes_of_the_elders.datagen;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.world.biome.ModMaterialRules;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public final class ModSurfaceRulesGenerator{
    public static void generateSurfaceRules() {
        EOTE.LOGGER.info("Creating Surface Rules for --->" + MOD_ID);
        var output = ModMaterialRules.makeRules();
        DataResult<JsonElement> dataResult = MaterialRules.MaterialRule.CODEC.encodeStart(JsonOps.INSTANCE, output);
        JsonElement element = dataResult.getOrThrow(true, (error) -> EOTE.LOGGER.info("Encountered an Error while Creating SurfaceRules, ERROR --->" + error));
        JsonObject json = new JsonObject();
        json.addProperty("sea_level", 63);
        json.addProperty("disable_mob_generation", false);
        json.addProperty("aquifers_enabled", true);
        json.addProperty("ore_veins_enabled", true);
        json.addProperty("legacy_random_source", false);
        json.add("default_block", configuredDefaultBlock());
        json.add("default_fluid", configuredDefaultFluid());
        json.add("noise", configuredNoise());
        boolean readWorked = readNoiseRouterFromTemplate(json,element);
        if (readWorked) {
            if (writeFile(json)) {
                EOTE.LOGGER.info("Surface Rules were Created Successfully! ✔");
            } else {
                EOTE.LOGGER.info("Surface Rules were Failed to be Written! ❌");
            }
        } else {
            EOTE.LOGGER.info("Surface Rules data from Template was Failed!!! ❌");
        }

    }

    public static JsonObject configuredDefaultBlock() {
        JsonObject defaultBlockJson = new JsonObject();
        defaultBlockJson.addProperty("Name", "minecraft:stone");
        return defaultBlockJson;
    }

    public static JsonObject configuredDefaultFluid() {
        JsonObject defaultFluidJson = new JsonObject();
        defaultFluidJson.addProperty("Name", "minecraft:water");
        JsonObject props = new JsonObject();
        props.addProperty("level", "0");
        defaultFluidJson.add("Properties", props);
        return defaultFluidJson;
    }

    public static JsonObject configuredNoise() {
        JsonObject noiseJson = new JsonObject();
        noiseJson.addProperty("min_y", -64);
        noiseJson.addProperty("height", 384);
        noiseJson.addProperty("size_horizontal", 1);
        noiseJson.addProperty("size_vertical", 2);
        return noiseJson;
    }

    public static boolean readNoiseRouterFromTemplate(JsonObject json,JsonElement element) {
        try (FileReader reader = new FileReader(getProjectDirectory(true))) {
            Object jsonData = JsonParser.parseReader(reader);
            if (jsonData instanceof JsonObject jsonObject) { //template file
                json.add("noise_router", jsonObject.get("noise_router"));
                json.add("spawn_target", jsonObject.get("spawn_target"));
                JsonObject surfaceRuleJson = new JsonObject();
                surfaceRuleJson.addProperty("type", "minecraft:sequence");
                surfaceRuleJson.add("sequence", element.getAsJsonObject().get("sequence").getAsJsonArray());
                json.add("surface_rule", surfaceRuleJson);
                json.get("surface_rule").getAsJsonObject().get("sequence").getAsJsonArray().addAll(jsonObject.get("surface_rule").getAsJsonObject().get("sequence").getAsJsonArray());
                return true;
            } else {
                EOTE.LOGGER.info(jsonData.getClass()+"");
            }
            return false;
        } catch (IOException e) {
            EOTE.LOGGER.info("Excaption happaned in file parse, reason -->"+e.getMessage());
            return false;
        }
    }

    public static boolean writeFile(JsonObject jsonObject) {
        File genFile = new File(getProjectDirectory(false)+"\\src\\main\\resources\\data\\echoes_of_the_elders\\worldgen\\noise_settings\\defense_dim_settings.json");
        if (!genFile.getParentFile().exists()){
            genFile.getParentFile().mkdirs();
        }
        try (FileWriter file = new FileWriter(genFile)) {
            file.write(jsonObject.toString());
            file.flush();
            file.close();
            return true;
        } catch (IOException e) {
            EOTE.LOGGER.info("Error -->"+e.getMessage());
            return false;
        }
    }

    public static String getProjectDirectory(boolean forTemplate) {
        File file = new File(Paths.get("").toAbsolutePath().toString());
        do {
            file = file.getParentFile();
        }
        while (!file.getName().equals("build"));
        file = file.getParentFile();
        String out;
        if (forTemplate){
            out = file.getAbsolutePath() + "\\scripts\\template.json";
        }else {
            out = file.getAbsolutePath();
        }

        File template = new File(out);
        if (template.exists()){
            return out;
        }else {
            return "";
        }

    }
}
