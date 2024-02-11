package com.bajookie.echoes_of_the_elders.system.Raid.waves;

import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class RaiderRole {
    public RaiderRole(){

    }
    public void applyItems(LivingEntity raider,int level){
        addHelmet(raider,level);
        addChestPlate(raider,level);
        addLeggings(raider, level);
        addBoots(raider, level);
        addMainHand(raider,level);
        addOffHand(raider, level);
    }
    public abstract void addHelmet(LivingEntity raider,int level);
    public abstract void addChestPlate(LivingEntity raider,int level);
    public abstract void addLeggings(LivingEntity raider,int level);
    public abstract void addBoots(LivingEntity raider,int level);
    public abstract void addMainHand(LivingEntity raider,int level);
    public abstract void addOffHand(LivingEntity raider,int level);
}
