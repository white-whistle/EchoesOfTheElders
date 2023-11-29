package com.bajookie.biotech.entity.custom;

import com.bajookie.biotech.BioTech;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FlowerDefenseEntity extends AnimalEntity {
    public FlowerDefenseEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class,5f));
        this.goalSelector.add(1, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createFlowerDefenseAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,15)
                .add(EntityAttributes.GENERIC_ARMOR,5f);
    }

    @Override
    public ItemStack eatFood(World world, ItemStack stack) {
        return Items.APPLE.getDefaultStack();
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        BioTech.LOGGER.info("dead");
        //ModRaid modRaid = new ModRaid(2,this,getWorld());
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
