package com.bajookie.echoes_of_the_elders.system.Raid;

import com.bajookie.echoes_of_the_elders.entity.custom.RaidTotemEntity;
import com.bajookie.echoes_of_the_elders.mixin.MobEntityAccessor;
import com.bajookie.echoes_of_the_elders.system.Capability.Capability;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.util.EntityUtil;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class RaidEnemyCapability extends Capability<LivingEntity> {
    private UUID raidTargetUUID;
    private static final float ENEMY_SEARCH_MODIFIER = 10;

    public RaidEnemyCapability(LivingEntity self) {
        super(self);

        if (self instanceof MobEntity mobEntity && mobEntity instanceof MobEntityAccessor mobEntityAccessor) {
            var world = mobEntity.getWorld();
            if (world.isClient) return;

            var targeting = mobEntityAccessor.getTargetSelector();
            var goals = mobEntityAccessor.getGoalSelector();

            targeting.add(0, new ActiveTargetGoal<>(mobEntity, RaidTotemEntity.class, true) {
                private int buildCooldown = 20;

                @Override
                public boolean canStart() {
                    super.canStart();
                    return true;
                }

                @Override
                protected Box getSearchBox(double distance) {
                    return this.mob.getBoundingBox().expand(distance * ENEMY_SEARCH_MODIFIER, 30, distance * ENEMY_SEARCH_MODIFIER);
                }

                @Override
                public boolean shouldContinue() {
                    var ret = super.shouldContinue();

                    if (ret) return true;

                    var raidTarget = getRaidTarget(mobEntity.getWorld());
                    if (this.mob.getNavigation().isIdle()) {
                        if (buildCooldown <= 0) {
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

                        return false;
                    }


                    return true;
                }

                @Override
                public void start() {
                    this.findClosestTarget();
                    super.start();
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

                    mobEntity.setPos(mobEntity.getX(), (int) (mobEntity.getY() - 1.1), mobEntity.getZ());
                    BlockState blockState = Blocks.AIR.getDefaultState();
                    world.setBlockState(blockPos, blockState);
                }

                private boolean isSameHeight() {
                    var target = mobEntity.getTarget();
                    if (target == null) return false;

                    return target.getBlockY() == mobEntity.getBlockY();
                }

                private void breakFront() {
                    var world = mobEntity.getWorld();

                    var pos = mobEntity.getPos();
                    var dir = mobEntity.getMovementDirection().getVector();

                    var target = getRaidTarget(world);

                    var iHeight = (int) Math.round(mobEntity.getHeight());

                    for (int i = 0; i < iHeight; i++) {
                        // break facing blocks
                        HitResult hitResult = world.raycast(new RaycastContext(pos.add(0, i, 0), pos.add(0, i, 0).add(new Vec3d(dir.getX(), dir.getY(), dir.getZ()).normalize()), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mobEntity));

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
                    if (mobEntity.squaredDistanceTo(target) > 1) {
                        var blockPos = mobEntity.getBlockPos().add(0, -1, 0).add(dir);

                        if (world.getBlockState(blockPos).isAir()) {
                            BlockState blockState = Blocks.COBBLESTONE.getDefaultState();
                            world.setBlockState(blockPos, blockState);
                        }
                    }

                }

                @Override
                protected void findClosestTarget() {
                    this.targetEntity = getRaidTarget(this.mob.getWorld());
                }
            });

            mobEntity.clearGoals(g -> g instanceof WanderAroundGoal);
        }
    }

    public void onDeath() {
        if (this.self == null) return;

        var objective = getRaidTarget(self.getWorld());

        ModCapabilities.RAID_OBJECTIVE.use(objective, o -> {
            o.onEnemyKilled(self);
        });
    }

    @Nullable
    public LivingEntity getRaidTarget(World world) {
        if (raidTargetUUID == null) return null;

        return (LivingEntity) EntityUtil.getEntityByUUID(world, raidTargetUUID);
    }

    public void setRaidTarget(LivingEntity livingEntity) {
        this.raidTargetUUID = livingEntity.getUuid();
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        if (raidTargetUUID != null) {
            nbt.putUuid("target", raidTargetUUID);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        if (nbt.contains("target")) {
            raidTargetUUID = nbt.getUuid("target");
        }
    }

    @Override
    public String toString() {
        return "RaidEnemyCapability{" +
                "raidTargetUUID=" + raidTargetUUID +
                '}';
    }
}
