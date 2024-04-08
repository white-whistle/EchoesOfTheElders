package com.bajookie.echoes_of_the_elders.datagen;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ItemMetadataJsonifier {
    record ItemMetadata(String item, int min, int max) {
    }

    public static void generateMetaFile() throws IOException {


        var outFile = "artifactItemMetadata";


        var itemMeta = ModItems.registeredModItems.stream().map(item -> {
            var clazz = item.getClass();
            var dropMeta = clazz.getAnnotation(DropCondition.RaidLevelBetween.class);
            int min = 0, max = Integer.MAX_VALUE;

            if (dropMeta != null) {
                min = dropMeta.min();
                max = dropMeta.max();
            }

            return new ItemMetadata(item.toString(), min, max);
        }).toArray(ItemMetadata[]::new);

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
