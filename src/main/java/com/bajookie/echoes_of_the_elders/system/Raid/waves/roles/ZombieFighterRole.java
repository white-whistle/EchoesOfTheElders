package com.bajookie.echoes_of_the_elders.system.Raid.waves.roles;

import com.bajookie.echoes_of_the_elders.system.Raid.waves.EquipmentSequence;
import com.bajookie.echoes_of_the_elders.system.Raid.waves.RaiderRole;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public final class ZombieFighterRole extends RaiderRole {
    @Override
    public void addHelmet(LivingEntity raider, int level) {
        ItemStack diamondV1 = new ItemStack(Items.DIAMOND_HELMET,1);
        diamondV1.addEnchantment(Enchantments.PROTECTION,1);
        EquipmentSequence sequence = new EquipmentSequence.SequenceBuilder(2,EquipmentSlot.HEAD)
                .addEquipment(Items.LEATHER_HELMET.getDefaultStack(),7)
                .addEquipment(Items.CHAINMAIL_HELMET.getDefaultStack(),12)
                .addEquipment(Items.IRON_HELMET.getDefaultStack(),17)
                .addEquipment(Items.DIAMOND_HELMET.getDefaultStack(),23)
                .addEquipment(diamondV1,25).build();
        sequence.equip(raider, level);
    }

    @Override
    public void addChestPlate(LivingEntity raider, int level) {
        EquipmentSequence sequence = new EquipmentSequence.SequenceBuilder(4,EquipmentSlot.CHEST)
                .addEquipment(Items.LEATHER_CHESTPLATE.getDefaultStack(),9)
                .addEquipment(Items.CHAINMAIL_CHESTPLATE.getDefaultStack(),14)
                .addEquipment(Items.IRON_CHESTPLATE.getDefaultStack(),19)
                .addEquipment(Items.DIAMOND_CHESTPLATE.getDefaultStack(),24)
                .addEquipment(Items.NETHERITE_CHESTPLATE.getDefaultStack(),29)
                .build();
        sequence.equip(raider, level);
    }

    @Override
    public void addLeggings(LivingEntity raider, int level) {
        EquipmentSequence sequence = new EquipmentSequence.SequenceBuilder(3,EquipmentSlot.LEGS)
                .addEquipment(Items.LEATHER_LEGGINGS.getDefaultStack(),8)
                .addEquipment(Items.CHAINMAIL_LEGGINGS.getDefaultStack(),13)
                .addEquipment(Items.IRON_LEGGINGS.getDefaultStack(),18)
                .addEquipment(Items.DIAMOND_LEGGINGS.getDefaultStack(),23)
                .addEquipment(Items.NETHERITE_LEGGINGS.getDefaultStack(),28)
                .build();
        sequence.equip(raider, level);
    }

    @Override
    public void addBoots(LivingEntity raider, int level) {
        EquipmentSequence sequence = new EquipmentSequence.SequenceBuilder(1,EquipmentSlot.FEET)
                .addEquipment(Items.LEATHER_BOOTS.getDefaultStack(),6)
                .addEquipment(Items.CHAINMAIL_BOOTS.getDefaultStack(),11)
                .addEquipment(Items.IRON_BOOTS.getDefaultStack(),17)
                .addEquipment(Items.DIAMOND_BOOTS.getDefaultStack(),21)
                .addEquipment(Items.NETHERITE_BOOTS.getDefaultStack(),27)
                .build();
        sequence.equip(raider, level);

    }

    @Override
    public void addMainHand(LivingEntity raider, int level) {
        EquipmentSequence sequence = new EquipmentSequence.SequenceBuilder(2,EquipmentSlot.MAINHAND)
                .addEquipment(Items.STICK.getDefaultStack(), 4)
                .addEquipment(Items.WOODEN_SWORD.getDefaultStack(),9)
                .addEquipment(Items.STONE_SWORD.getDefaultStack(),14)
                .addEquipment(Items.IRON_SWORD.getDefaultStack(),19)
                .addEquipment(Items.DIAMOND_SWORD.getDefaultStack(),24)
                .addEquipment(Items.NETHERITE_SWORD.getDefaultStack(),29)
                .build();
        sequence.equip(raider, level);
    }

    @Override
    public void addOffHand(LivingEntity raider, int level) {
        if (level>25){
            raider.equipStack(EquipmentSlot.OFFHAND,Items.TOTEM_OF_UNDYING.getDefaultStack());
        }
    }
}
