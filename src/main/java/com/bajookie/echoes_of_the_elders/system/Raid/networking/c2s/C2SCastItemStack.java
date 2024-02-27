package com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.PacketByteBuf;

public record C2SCastItemStack(EquipmentSlot slot) implements FabricPacket {
    public static final PacketType<C2SCastItemStack> TYPE = PacketType.create(new ModIdentifier("c2s_cast_itemstack"), C2SCastItemStack::new);

    public C2SCastItemStack(PacketByteBuf buf) {
        this(EquipmentSlot.valueOf(buf.readString()));
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(slot.name());
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void send(EquipmentSlot slot) {
        ClientPlayNetworking.send(new C2SCastItemStack(slot));
    }
}
