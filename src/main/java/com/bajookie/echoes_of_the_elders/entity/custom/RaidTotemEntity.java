package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RaidTotemEntity extends AnimalEntity {
    public RaidTotemEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);

        ModCapabilities.RAID_OBJECTIVE.attach(this);
    }

    @Override
    protected void initGoals() {
    }

    public static DefaultAttributeContainer.Builder createFlowerDefenseAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_ARMOR, 5f);
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        //    noop
    }

    @Override
    public ItemStack eatFood(World world, ItemStack stack) {
        return Items.APPLE.getDefaultStack();
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
