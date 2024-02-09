package com.bajookie.echoes_of_the_elders.system.Raid.waves;

import java.util.List;
import java.util.stream.Collectors;

public class WaveGarage {
    public static void assembleWave(int level){
        List<EntityFeatureRecord> availableEntities = WaveFeatures.EntityFeature.getEntityRecordList();
        availableEntities = availableEntities.stream().filter(record -> record.canApply(level)).collect(Collectors.toList());
        List<EntityToolFeatureRecord> availableTools = WaveFeatures.EntityToolFeature.getEntityToolRecordList();
        availableTools = availableTools.stream().filter(record -> record.canApply(level)).collect(Collectors.toList());
        int entityWeightSum = availableEntities.stream().
    }
}
