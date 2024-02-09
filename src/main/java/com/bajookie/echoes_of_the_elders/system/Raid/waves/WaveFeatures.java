package com.bajookie.echoes_of_the_elders.system.Raid.waves;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import oshi.util.tuples.Pair;
import oshi.util.tuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WaveFeatures {

    public static void registerWaveRecords(){
        EntityFeature.bootstrap();
        EntityToolFeature.bootstrap();
        EntityEffectFeature.bootstrap();
    }

    public static class EntityFeature {
        private static final List<EntityFeatureRecord> featureList = new ArrayList<>();

        public static void bootstrap(){
            register(0, 10, 1, ZombieEntity.class, 10, Optional.empty(), Optional.empty());
            register(0, 10, 1, SkeletonEntity.class, 9, Optional.empty(), Optional.empty());
        }

        private static void register(int minLevel, int weight, int levelStep, Class<? extends LivingEntity> entity, int baseCount, Optional<Integer> plusMinusRandom, Optional<Pair<Integer, Integer>> commonWaveAndMultiplier) {
            featureList.add(new EntityFeatureRecord(minLevel,weight,levelStep,entity,baseCount,plusMinusRandom,commonWaveAndMultiplier));
        }
        public static List<EntityFeatureRecord> getEntityRecordList(){
            return featureList;
        }

    }
    public static class EntityEffectFeature {
        public static void bootstrap(){
        }
    }
    public static class EntityToolFeature {
        private static final List<EntityToolFeatureRecord> featureList = new ArrayList<>();

        public static void bootstrap(){
            register(0, EquipmentSlot.MAINHAND, Items.WOODEN_SHOVEL,10,1,20,new Triplet<>(false,0,0));
            register(1,EquipmentSlot.MAINHAND,Items.WOODEN_AXE,6,1,15,new Triplet<>(false,0,0));
        }

        private static void register(int minLevel, EquipmentSlot equipmentSlot, Item item,int weight,int step,int peak, Triplet<Boolean,Integer,Integer> enchantability) {
            featureList.add(new EntityToolFeatureRecord(minLevel,equipmentSlot,item,weight,step,peak,new Triplet<> (false,0,0)));
        }
        public static List<EntityToolFeatureRecord> getEntityToolRecordList(){
            return featureList;
        }
    }
}
