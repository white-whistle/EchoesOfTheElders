package com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public record RequestLeftClickAbilitySync(ItemStack stack) implements FabricPacket {
    public static final PacketType<RequestLeftClickAbilitySync> TYPE = PacketType.create(new ModIdentifier("c2s_left_click_ability_sync"), RequestLeftClickAbilitySync::new);
    public RequestLeftClickAbilitySync(PacketByteBuf buf){
        this(buf.readItemStack());
    }
    @Override
    public void write(PacketByteBuf buf) {
        buf.writeItemStack(this.stack);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
    public static void send(ItemStack stack) {
        ClientPlayNetworking.send(new RequestLeftClickAbilitySync(stack));
    }
}
