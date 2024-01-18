package com.bajookie.echoes_of_the_elders.system.Raid;

import com.bajookie.echoes_of_the_elders.system.Capability.Capability;
import com.bajookie.echoes_of_the_elders.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class RaidEnemyCapability implements Capability {
    private UUID raidTargetUUID;

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
