package com.bajookie.echoes_of_the_elders.datagen;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ItemMetadataJsonifier {
    public static void generateMetaFile() throws IOException {
        
        var outFile = "artifactItemMetadata";

        var itemMeta = ModItems.registeredModItems.stream().map(item -> {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("item", item.toString());

            var clazz = item.getClass();
            var dropMeta = clazz.getAnnotation(DropCondition.RaidLevelBetween.class);

            if (dropMeta != null) {
                var dropData = new JsonObject();
                dropData.addProperty("min", dropMeta.min());
                dropData.addProperty("max", dropMeta.max());

                jsonObject.add("dropData", dropData);
            }

            if (item instanceof IRaidReward iRaidReward) {
                jsonObject.addProperty("isReward", true);
                jsonObject.addProperty("rarity", iRaidReward.getRarity().toString());
            }

            return jsonObject;
        }).toArray(JsonObject[]::new);

        writeFile(itemMeta, outFile);
    }

    public static boolean writeFile(Object o, String fileName) {
        File genFile = new File(ModSurfaceRulesGenerator.getProjectDirectory(false) + "\\scripts\\dist\\" + fileName + ".json");
        if (!genFile.getParentFile().exists()) {
            genFile.getParentFile().mkdirs();
        }
        try (FileWriter file = new FileWriter(genFile)) {
            Gson gson = new Gson();

            gson.toJson(o, file);
            return true;
        } catch (IOException e) {
            EOTE.LOGGER.info("Error -->" + e.getMessage());
            return false;
        }
    }
}
