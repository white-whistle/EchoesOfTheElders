package com.bajookie.echoes_of_the_elders.system.Raid;

import com.bajookie.echoes_of_the_elders.entity.custom.RaidTotemEntity;
import com.bajookie.echoes_of_the_elders.mixin.MobEntityAccessor;
import com.bajookie.echoes_of_the_elders.system.Capability.Capability;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.util.EntityUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
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
            var targeting = mobEntityAccessor.getTargetSelector();
            var goals = mobEntityAccessor.getGoalSelector();

            targeting.add(0, new ActiveTargetGoal<>(mobEntity, RaidTotemEntity.class, true) {
                // @Override
                // public boolean canStart() {
                //     return true;
                // }

                @Override
                protected Box getSearchBox(double distance) {
                    return this.mob.getBoundingBox().expand(distance * ENEMY_SEARCH_MODIFIER, 30, distance * ENEMY_SEARCH_MODIFIER);
                }

                // @Override
                // public boolean shouldContinue() {
                //     return true;
                // }

                // @Override
                // protected void findClosestTarget() {
                //     this.targetEntity = getRaidTarget(this.mob.getWorld());
                //     // this.targetEntity = (LivingEntity) EntityUtil.getEntityByUUID(this.mob.getWorld(), raidTargetUUID);
                // }
            });

            goals.add(0, new Goal() {
                private Vec3d lastPos;
                private int inPlaceTicks = 0;
                private int buildCooldown = 0;

                private void reset() {
                    lastPos = mobEntity.getPos();
                    ;
                    inPlaceTicks = 0;
                    buildCooldown = 0;
                }

                private boolean checkMovement() {
                    var currPos = mobEntity.getPos();
                    // System.out.println("check");

                    if (lastPos == null) {
                        lastPos = currPos;
                        return false;
                    }

                    // System.out.println("dist" + currPos.distanceTo(lastPos));
                    if (currPos.distanceTo(lastPos) < 2) {
                        lastPos = currPos;
                        return true;
                    }

                    reset();
                    return false;
                }

                @Override
                public boolean canStart() {
                    // System.out.println("can start?!");
                    if (mobEntity.getTarget() == null) return false;
                    // if (isSameHeight()) return false;

                    // System.out.println("target!");
                    if (checkMovement()) {
                        System.out.println(inPlaceTicks);
                        // System.out.println("Movement!?");
                        if (inPlaceTicks > 20 * 10) {
                            // inPlaceTicks = 10;
                            return true;
                        }

                        inPlaceTicks++;
                        return false;
                    }

                    return false;
                }

                @Override
                public boolean shouldContinue() {
                    var target = mobEntity.getTarget();
                    if (target == null) return false;

                    return checkMovement();

                    // return true;
                    // return isSameHeight();
                }

                @Override
                public void start() {
                    System.out.println("Here");
                    if (buildCooldown <= 0) {
                        var target = mobEntity.getTarget();
                        if (target == null) return;

                        if (target.getY() < mobEntity.getY()) {
                            breakDown();
                        } else if (target.getY() > mobEntity.getY()) {
                            buildUp();
                        } else {
                            System.out.println("Got here??");
                            breakFront();
                        }


                        buildCooldown = 20;
                    } else {
                        buildCooldown--;
                    }
                }

                private void buildUp() {
                    var world = mobEntity.getWorld();
                    var blockPos = mobEntity.getBlockPos();

                    mobEntity.setPos(mobEntity.getX(), mobEntity.getY() + 1.1, mobEntity.getZ());
                    BlockState blockState = Blocks.COBBLESTONE.getDefaultState();
                    world.setBlockState(blockPos, blockState);
                }

                private void breakDown() {
                    var world = mobEntity.getWorld();
                    var blockPos = mobEntity.getBlockPos().add(0, -1, 0);

                    mobEntity.setPos(mobEntity.getX(), mobEntity.getY() - 1.1, mobEntity.getZ());
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
                    var vec3d = mobEntity.getRotationVec(0);

                    HitResult hitResult = world.raycast(new RaycastContext(pos.add(0, Math.random() * mobEntity.getHeight(), 0), vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mobEntity));

                    if (hitResult.getType() == HitResult.Type.BLOCK) {
                        var blockHit = (BlockHitResult) hitResult;
                        var blockPos = blockHit.getBlockPos();

                        mobEntity.setPos(mobEntity.getX(), mobEntity.getY() - 1.1, mobEntity.getZ());
                        BlockState blockState = Blocks.AIR.getDefaultState();
                        world.setBlockState(blockPos, blockState);
                    }

                }
            });

            mobEntity.clearGoals(g -> g instanceof WanderAroundGoal);

            // mobEntity.setStepHeight(3);
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
