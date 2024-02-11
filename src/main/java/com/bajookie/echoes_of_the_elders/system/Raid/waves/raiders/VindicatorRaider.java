package com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders;

import com.bajookie.echoes_of_the_elders.system.Raid.waves.WaveFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.Random;

public class VindicatorRaider extends RaidEntityFeature<VindicatorEntity>{
    public VindicatorRaider(int minimumLevel, int step, int baseCount) {
        super(EntityType.VINDICATOR, minimumLevel, step, baseCount);
    }

    @Override
    public VindicatorEntity makeEntity(World world, int level) {
        var entity = this.entityType.create(world);
        this.equipEntity(entity,level);
        return entity;
    }

    @Override
    public void equipEntity(VindicatorEntity entity, int level) {
        entity.equipStack(EquipmentSlot.MAINHAND,new ItemStack(Items.IRON_AXE));
        Random r = new Random();
        if (r.nextInt(2) == 1){
            entity.equipStack(EquipmentSlot.OFFHAND,new ItemStack(Items.IRON_AXE));
        }
        WaveFeatures.EntityToolFeature.CHEST_PLATES.equip(entity,level);
    }

    @Override
    public void buffEntity(VindicatorEntity entity, int level) {
    }
}
