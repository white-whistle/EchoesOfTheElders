package com.bajookie.echoes_of_the_elders.system.Raid.networking.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.minecraft.network.PacketByteBuf;

public abstract class IntPacket implements FabricPacket {
    public final int value;

    public IntPacket(int v) {
        this.value = v;
    }

    public IntPacket(PacketByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(value);
    }
}
