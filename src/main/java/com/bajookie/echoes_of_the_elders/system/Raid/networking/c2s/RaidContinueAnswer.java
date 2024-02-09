package com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s;

import com.bajookie.echoes_of_the_elders.system.Raid.RaidObjectiveCapability;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public record RaidContinueAnswer(int answer, UUID objectiveUuid) implements FabricPacket {
    public static final PacketType<RaidContinueAnswer> TYPE = PacketType.create(new ModIdentifier("raid_continue_answer"), RaidContinueAnswer::new);

    public RaidContinueAnswer(PacketByteBuf buf) {
        this(buf.readInt(), buf.readUuid());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.answer());
        buf.writeUuid(this.objectiveUuid);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void send(RaidObjectiveCapability.RaidAnswer answer, UUID objectiveUuid) {
        ClientPlayNetworking.send(new RaidContinueAnswer(answer.value, objectiveUuid));
    }
}
