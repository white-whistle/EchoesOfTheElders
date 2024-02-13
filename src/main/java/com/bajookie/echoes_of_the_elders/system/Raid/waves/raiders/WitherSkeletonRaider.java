package com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class WitherSkeletonRaider extends RaidEntityFeature<WitherSkeletonEntity>{
    public WitherSkeletonRaider(int minimumLevel, int step, int baseCount) {
        super(EntityType.WITHER_SKELETON, minimumLevel, step, baseCount);
    }

    @Override
    public WitherSkeletonEntity makeEntity(World world, int level) {
        var entity = this.entityType.create(world);
        this.equipEntity(entity,level);
        return entity;
    }

    @Override
    public void equipEntity(WitherSkeletonEntity entity, int level) {
        entity.equipStack(EquipmentSlot.MAINHAND,new ItemStack(Items.DIAMOND_SWORD));
    }

    @Override
    public void buffEntity(WitherSkeletonEntity entity, int level) {

    }
}
