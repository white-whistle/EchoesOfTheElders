package com.bajookie.echoes_of_the_elders.system.Raid;

import com.bajookie.echoes_of_the_elders.entity.custom.RaidTotemEntity;
import com.bajookie.echoes_of_the_elders.mixin.MobEntityAccessor;
import com.bajookie.echoes_of_the_elders.system.Capability.Capability;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
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

            targeting.add(0, new ActiveTargetGoal<>(mobEntity, RaidTotemEntity.class, false) {
                @Override
                protected Box getSearchBox(double distance) {
                    return this.mob.getBoundingBox().expand(distance * ENEMY_SEARCH_MODIFIER, 30, distance * ENEMY_SEARCH_MODIFIER);
                }
            });
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
