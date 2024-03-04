package com.bajookie.echoes_of_the_elders.entity.goals;

import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public abstract class BuildAndBreakGoal extends Goal {
    private int buildCooldown = 20;

    MobEntity mobEntity;
    LivingEntity raidTarget;

    public BuildAndBreakGoal(MobEntity mobEntity) {
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

    private void buildUp() {
        var world = mobEntity.getWorld();
        var blockPos = mobEntity.getBlockPos();

        mobEntity.setPos(mobEntity.getX(), (int) (mobEntity.getY() + 1.1), mobEntity.getZ());
        BlockState blockState = Blocks.COBBLESTONE.getDefaultState();
        world.setBlockState(blockPos, blockState);
    }

    private void breakDown() {
        var world = mobEntity.getWorld();
        var blockPos = mobEntity.getBlockPos().add(0, -1, 0);

        // mobEntity.setPos(mobEntity.getX(), (int) (mobEntity.getY() - 1.1), mobEntity.getZ());
        BlockState blockState = Blocks.AIR.getDefaultState();
        world.setBlockState(blockPos, blockState);
    }

    private void breakFront() {
        var world = mobEntity.getWorld();

        var pos = mobEntity.getPos();
        var dir = raidTarget.getPos().subtract(mobEntity.getPos()).normalize();

        var iHeight = (int) Math.round(mobEntity.getHeight());

        for (int i = 0; i < iHeight; i++) {
            // break facing blocks
            HitResult hitResult = world.raycast(new RaycastContext(pos.add(0, i, 0), pos.add(0, i, 0).add(new Vec3d(dir.getX(), 0, dir.getZ()).normalize()), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mobEntity));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                var blockHit = (BlockHitResult) hitResult;
                var blockPos = blockHit.getBlockPos();

                BlockState blockState = Blocks.AIR.getDefaultState();
                world.setBlockState(blockPos, blockState);

                return;
            }
        }

        var lookDir = VectorUtil.pitchYawRollToDirection(mobEntity.getPitch(), mobEntity.getYaw(), 0);
        HitResult hitResult = world.raycast(new RaycastContext(mobEntity.getEyePos(), mobEntity.getEyePos().add(lookDir.x, lookDir.y, lookDir.z), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mobEntity));

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            var blockHit = (BlockHitResult) hitResult;
            var blockPos = blockHit.getBlockPos();

            BlockState blockState = Blocks.AIR.getDefaultState();
            world.setBlockState(blockPos, blockState);

            return;
        }

        // build out from tower
        if (mobEntity.squaredDistanceTo(raidTarget) > 1) {
            var raidDir = raidTarget.getPos().subtract(mobEntity.getPos()).normalize();
            var blockPos = mobEntity.getBlockPos().add((int) Math.round(dir.x), -1, (int) Math.round(dir.z));

            if (world.getBlockState(blockPos).isAir()) {
                BlockState blockState = Blocks.COBBLESTONE.getDefaultState();
                world.setBlockState(blockPos, blockState);
            }
        }

    }
}
