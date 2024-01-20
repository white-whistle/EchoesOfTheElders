package com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public record RequestCapabilitySync(UUID entityUuid) implements FabricPacket {
    public static final PacketType<RequestCapabilitySync> TYPE = PacketType.create(new ModIdentifier("c2s_capability_sync"), RequestCapabilitySync::new);

    public RequestCapabilitySync(PacketByteBuf buf) {
        this(buf.readUuid());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeUuid(this.entityUuid);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void send(LivingEntity entityToSync) {
        ClientPlayNetworking.send(new RequestCapabilitySync(entityToSync.getUuid()));
    }
}
