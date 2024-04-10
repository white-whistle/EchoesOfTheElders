package com.bajookie.echoes_of_the_elders.datagen;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.Text.ModText;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TooltipSection;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ItemMetadataJsonifier {
    public static void generateMetaFile() throws IOException {

        var outFile = "artifactItemMetadata";
        var gson = new Gson();

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

            if (item instanceof IArtifact iArtifact) {
                var stack = item.getDefaultStack();
                jsonObject.addProperty("isArtifact", true);
                jsonObject.addProperty("artifactMaxStack", iArtifact.getArtifactMaxStack());

                var tags = gson.toJsonTree(iArtifact.getTags().stream().map(Enum::toString).toArray());

                jsonObject.add("tags", tags);
                JsonArray abilities = new JsonArray();
                for (var ability : iArtifact.getAbilities(stack)) {
                    var abilityJson = new JsonObject();
                    abilityJson.addProperty("name", ability.name);
                    var abilityInfo = new JsonArray();
                    abilityJson.add("info", abilityInfo);

                    var mockTooltipSection = new TooltipSection.TooltipSectionContext() {
                        @Override
                        public TooltipSection.TooltipSectionContext line(String id, TextArgs args) {
                            var lineJson = new JsonObject();
                            lineJson.addProperty("name", id);
                            if (args != null) {
                                lineJson.add("args", args.toJson());
                            }

                            abilityInfo.add(lineJson);
                            return this;
                        }
                    };

                    ability.appendTooltipInfo(stack, null, List.of(), null, mockTooltipSection);

                    abilities.add(abilityJson);
                }
                jsonObject.add("abilities", abilities);
            }

            return jsonObject;
        }).toArray(JsonObject[]::new);

        JsonObject meta = new JsonObject();

        meta.add("items", gson.toJsonTree(itemMeta));

        var modTextComponents = gson.toJsonTree(ModText.registeredTags.keySet().toArray());
        meta.add("richTextComponents", modTextComponents);

        writeFile(meta, outFile);
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
