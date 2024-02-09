package com.bajookie.echoes_of_the_elders.system.Raid.waves;

import com.bajookie.echoes_of_the_elders.system.Raid.waves.equipments.EquipmentSequence;
import com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders.PhantomRaider;
import com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders.RaidEntityFeature;
import com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders.SkeletonRaider;
import com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders.ZombieRaider;
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
import java.util.Random;
import java.util.stream.Collectors;

public class WaveFeatures {

    public static void registerWaveRecords() {
        EntityFeature.bootstrap();
        EntityToolFeature.bootstrap();
        EntityEffectFeature.bootstrap();
    }

    public static class EntityFeature {
        public static List<RaidEntityFeature<? extends LivingEntity>> list = new ArrayList<>();
        public static ZombieRaider ZOMBIE_RAIDER = (ZombieRaider) register(new ZombieRaider(1, 1, 5));
        public static SkeletonRaider SKELETON_RAIDER = (SkeletonRaider) register(new SkeletonRaider(1, 1, 5));
        public static PhantomRaider PHANTOM_RAIDER = (PhantomRaider) register(new PhantomRaider(5,1,4));

        public static void bootstrap() {
        }
        private static RaidEntityFeature<? extends LivingEntity> register(RaidEntityFeature<? extends LivingEntity> raidEntityFeature){
            list.add(raidEntityFeature);
            return raidEntityFeature;
        }
    }

    public static class EntityEffectFeature {
        public static void bootstrap() {
        }
    }

    public static class EntityToolFeature {
        public static EquipmentSequence ZOMBIE_HAND = new EquipmentSequence.SequenceBuilder(1,EquipmentSlot.MAINHAND).addEquipment(Items.WOODEN_SHOVEL,2).addEquipment(Items.WOODEN_AXE,4).build();
        public static EquipmentSequence HELMETS = new EquipmentSequence.SequenceBuilder(2,EquipmentSlot.HEAD).addEquipment(Items.LEATHER_HELMET,3).addEquipment(Items.GOLDEN_HELMET,4).build();
        public static void bootstrap() {
        }

    }
    public static List<RaidEntityFeature<? extends LivingEntity>> getRaidersList(int level){
        List<RaidEntityFeature<? extends LivingEntity>> list = EntityFeature.list.stream().filter(raidEntityFeature -> raidEntityFeature.canApply(level)).collect(Collectors.toList());
        List<RaidEntityFeature<? extends LivingEntity>> listFinal = new ArrayList<>();

        if (list.size()<=3){
            return list;
        } else {
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                listFinal.add(list.get(random.nextInt(list.size())));
            }
            return listFinal;
        }
    }
}
