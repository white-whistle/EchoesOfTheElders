package com.bajookie.echoes_of_the_elders.system.Raid.waves;

import net.minecraft.entity.LivingEntity;
import oshi.util.tuples.Pair;

import java.util.Optional;
import java.util.Random;

public record EntityFeatureRecord(int minLevel, int weight, int levelStep, Class<? extends LivingEntity> entity, int baseCount, Optional<Integer> plusMinusRandom, Optional<Pair<Integer,Integer>> commonWaveAndMultiplier) {
    private static final Random random = new Random();
    public EntityFeatureRecord calculate(int level){
        int weighter = commonWaveAndMultiplier.map(integerIntegerPair -> weight * integerIntegerPair.getB()).orElse(weight);
        int count = plusMinusRandom.map(integer ->{
            return baseCount+ random.nextInt(integer*-1,integer+1);
        }).orElse(baseCount);
        count = (int) (weight + Math.floor(((level-minLevel)/levelStep)));
        return new EntityFeatureRecord(minLevel,weighter,levelStep,entity,count,plusMinusRandom,commonWaveAndMultiplier);
    }
    public boolean canApply(int level){
        return minLevel<=level;
    }
}
