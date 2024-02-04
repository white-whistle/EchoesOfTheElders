package com.bajookie.echoes_of_the_elders.system.Raid.networking.s2c;

import com.bajookie.echoes_of_the_elders.system.Capability.Capability;
import com.bajookie.echoes_of_the_elders.system.Capability.IHasCapability;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public record SyncSingleCapability<T>(UUID entityUuid, String name, Capability<T> capability) implements FabricPacket {
    public static final PacketType<SyncSingleCapability> TYPE = PacketType.create(new ModIdentifier("s2c_sync_single_capability"), SyncSingleCapability::new);

    private static Capability<?> getCapabilityFromNbt(String name, NbtCompound nbt) {
        var capabilityFactory = ModCapabilities.lookup.get(name);
        var capability = capabilityFactory.apply(null);

        capability.readFromNbt(nbt);

        return capability;
    }

    public SyncSingleCapability(PacketByteBuf buf) {
        this(buf.readUuid(), buf.readString(), buf.readNbt());
    }

    public SyncSingleCapability(UUID uuid, String name, NbtCompound nbt) {
        this(uuid, name, (Capability<T>) getCapabilityFromNbt(name, nbt));
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeUuid(this.entityUuid);
        buf.writeString(this.name);

        var nbt = new NbtCompound();
        this.capability().writeToNbt(nbt);

        buf.writeNbt(nbt);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void send(ServerPlayerEntity player, LivingEntity livingEntity, String name) {
        if (livingEntity instanceof IHasCapability iHasCapability) {
            if (iHasCapability.echoesOfTheElders$hasCapabilities()) {
                ServerPlayNetworking.send(player, new SyncSingleCapability(livingEntity.getUuid(), name, iHasCapability.echoesOfTheElders$getCapabilities().get(name)));
            }
        }
    }
}
