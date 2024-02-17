package com.bajookie.echoes_of_the_elders.system.Raid.networking.s2c;

import com.bajookie.echoes_of_the_elders.system.Capability.Capabilities;
import com.bajookie.echoes_of_the_elders.system.Capability.IHasCapability;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;
import java.util.UUID;

public record CapabilitySync(UUID entityUuid, Capabilities capabilities) implements FabricPacket {
    public static final PacketType<CapabilitySync> TYPE = PacketType.create(new ModIdentifier("s2c_capability_sync"), CapabilitySync::new);

    public CapabilitySync(PacketByteBuf buf) {
        this(buf.readUuid(), new Capabilities.UnboundCapabilities(Objects.requireNonNull(buf.readNbt())));
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeUuid(this.entityUuid);

        var nbt = new NbtCompound();
        Capabilities.writeCapabilities(this.capabilities, nbt);

        buf.writeNbt(nbt);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void send(ServerPlayerEntity player, LivingEntity livingEntity) {
        if (livingEntity instanceof IHasCapability iHasCapability) {
            if (iHasCapability.echoesOfTheElders$hasCapabilities()) {
                ServerPlayNetworking.send(player, new CapabilitySync(livingEntity.getUuid(), iHasCapability.echoesOfTheElders$getCapabilities()));
            }
        }
    }
}
