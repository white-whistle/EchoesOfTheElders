package com.bajookie.echoes_of_the_elders.system.Raid.waves;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import oshi.util.tuples.Triplet;

import java.util.Map;

public record EntityToolFeatureRecord(int minLevel, EquipmentSlot equipmentSlot, Item item, int weight,int step,int peak, Triplet<Boolean,Integer,Integer> enchantability) {

    public void setTool( LivingEntity entity){
        ItemStack stack = new ItemStack(item);
        entity.equipStack(equipmentSlot,stack);
    }
    public EntityToolFeatureRecord progress(int level){

        int _weight = weight;
        if (level <= peak){
            _weight += (level-minLevel)*step;
        } else {
            _weight = (weight+(peak-minLevel)*step)- (level-peak)*step;
            if (_weight<= 0) _weight = 0;
        }
        return new EntityToolFeatureRecord(minLevel,equipmentSlot,item,_weight,step,peak,enchantability);
    }

    public boolean canApply(int level){
        return minLevel<=level;
    }

    public static enum Enchant{

    }
}
