package com.bajookie.echoes_of_the_elders.system.Raid.waves;

import com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;

public class WaveFeatures {

    public static void registerWaveRecords() {
        EntityFeature.bootstrap();
        EntityToolFeature.bootstrap();
        EntityEffectFeature.bootstrap();
        EntityRoleFeature.bootstrap();
    }

    public static class EntityFeature {
        public static List<RaidEntityFeature<? extends LivingEntity>> list = new ArrayList<>();
        public static ZombieRaider ZOMBIE_RAIDER = (ZombieRaider) register(new ZombieRaider(0, 1, 5));
        public static SkeletonRaider SKELETON_RAIDER = (SkeletonRaider) register(new SkeletonRaider(0, 1, 5));
        public static PhantomRaider PHANTOM_RAIDER = (PhantomRaider) register(new PhantomRaider(5,1,4));
        public static VindicatorRaider VINDICATOR_RAIDER = (VindicatorRaider) register(new VindicatorRaider(7,1,2));

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

    public static class EntityRoleFeature{

        public static void bootstrap(){}
    }

    public static class EntityToolFeature {
        public static EquipmentSequence ZOMBIE_HAND = new EquipmentSequence.SequenceBuilder(1,EquipmentSlot.MAINHAND).addEquipment(Items.WOODEN_SHOVEL.getDefaultStack(),2).addEquipment(Items.WOODEN_AXE.getDefaultStack(),4).build();
        public static EquipmentSequence HELMETS = new EquipmentSequence.SequenceBuilder(2,EquipmentSlot.HEAD).addEquipment(Items.LEATHER_HELMET.getDefaultStack(),7).addEquipment(Items.CHAINMAIL_HELMET.getDefaultStack(),12).build();
        public static EquipmentSequence CHEST_PLATES = new EquipmentSequence.SequenceBuilder(4,EquipmentSlot.CHEST).addEquipment(Items.LEATHER_CHESTPLATE.getDefaultStack(),9).addEquipment(Items.CHAINMAIL_CHESTPLATE.getDefaultStack(),14).build();
        public static EquipmentSequence LEGGINGS = new EquipmentSequence.SequenceBuilder(3,EquipmentSlot.LEGS).addEquipment(Items.LEATHER_LEGGINGS.getDefaultStack(),8).addEquipment(Items.CHAINMAIL_LEGGINGS.getDefaultStack(),13).build();
        public static EquipmentSequence BOOTS = new EquipmentSequence.SequenceBuilder(1,EquipmentSlot.FEET).addEquipment(Items.LEATHER_BOOTS.getDefaultStack(),6).addEquipment(Items.CHAINMAIL_BOOTS.getDefaultStack(),11).build();
        public static EquipmentSequence BOW = new EquipmentSequence.SequenceBuilder(3,EquipmentSlot.MAINHAND).addEquipment(Items.BOW.getDefaultStack(),4).build();
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
    public static List<LivingEntity> getEntities(World world, int level){
        List<LivingEntity> list = new ArrayList<>();
        var members = getRaidersList(level);
        for (RaidEntityFeature<? extends LivingEntity> feature : members){
            for (int i=0;i< feature.getCount(level);i++){
                list.add(feature.makeEntity(world,level));
            }
        }
        return list;
    }
}
