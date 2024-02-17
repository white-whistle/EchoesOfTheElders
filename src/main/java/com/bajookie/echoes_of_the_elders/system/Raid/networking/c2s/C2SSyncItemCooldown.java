package com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;

public record C2SSyncItemCooldown(Item item, int duration) implements FabricPacket {
    public static final PacketType<C2SSyncItemCooldown> TYPE = PacketType.create(new ModIdentifier("c2s_sync_player_cooldown"), C2SSyncItemCooldown::new);

    public C2SSyncItemCooldown(PacketByteBuf buf) {
        this(Registries.ITEM.get(buf.readIdentifier()), buf.readInt());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeIdentifier(Registries.ITEM.getId(item));
        buf.writeInt(duration);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void send(Item item, int duration) {
        ClientPlayNetworking.send(new C2SSyncItemCooldown(item, duration));
    }
}
