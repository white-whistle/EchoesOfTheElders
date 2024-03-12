package com.bajookie.echoes_of_the_elders.entity.goals;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.entity.custom.ZomBeeEntity;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

public abstract class SummonVehicleAndReachObjectiveGoal extends Goal {
    private int buildCooldown = 20;

    MobEntity mobEntity;
    LivingEntity raidTarget;

    public SummonVehicleAndReachObjectiveGoal(MobEntity mobEntity) {
        this.mobEntity = mobEntity;
    }

    public abstract LivingEntity getRaidTarget();

    @Override
    public boolean canStart() {
        return mobEntity.getNavigation().isIdle();
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }

    @Override
    public void tick() {
        if (raidTarget == null) {
            this.raidTarget = getRaidTarget();
        }


        if (buildCooldown <= 0) {
            if (mobEntity instanceof ZomBeeEntity) {
                tunnel();
                buildCooldown = 4;
                return;
            }

            if (!mobEntity.isOnGround()) {
                return;
            }


            if (raidTarget.getBlockY() < mobEntity.getBlockY()) {
                breakDown();
            } else if (raidTarget.getBlockY() > mobEntity.getBlockY()) {
                buildUp();
            } else {
                breakFront();
            }
            buildCooldown = 4;
        } else {
            buildCooldown--;
        }
    }

    private void tunnel() {
        var dir = raidTarget.getPos().subtract(mobEntity.getPos()).normalize();
        var newPos = mobEntity.getPos().add(dir.normalize().multiply(0.6));
        mobEntity.setPos(newPos.x, newPos.y, newPos.z);
    }

    private void buildUp() {
        if (!mobEntity.hasVehicle()) {
            var world = mobEntity.getWorld();

            var zombeeVehicle = ModEntities.ZOMBEE_ENTITY_TYPE.create(world);
            if (zombeeVehicle == null) return;

            var pos = mobEntity.getPos();
            zombeeVehicle.setPos(pos.x, pos.y, pos.z);

            world.spawnEntity(zombeeVehicle);

            ModCapabilities.RAID_OBJECTIVE.use(raidTarget, o -> {
                o.addEnemy(zombeeVehicle);
            });

            mobEntity.startRiding(zombeeVehicle);
        }
    }

    private void breakDown() {
        var blockPos = mobEntity.getBlockPos().add(0, -1, 0);

        mobEntity.setPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ());
    }

    private void breakFront() {
        var world = mobEntity.getWorld();

        var pos = mobEntity.getPos();
        var dir = raidTarget.getPos().subtract(mobEntity.getPos()).normalize();

        var newPos = pos.add(dir);
        mobEntity.setPos(newPos.x, newPos.y, newPos.z);

        if (world.getBlockState(mobEntity.getBlockPos().add(0, -1, 0)).isAir()) {
            buildUp();
        }

    }
}
