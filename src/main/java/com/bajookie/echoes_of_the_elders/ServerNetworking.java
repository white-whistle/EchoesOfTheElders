package com.bajookie.echoes_of_the_elders;

import com.bajookie.echoes_of_the_elders.item.ILeftClickAbility;
import com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s.RequestCapabilitySync;
import com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s.RequestLeftClickAbilitySync;
import com.bajookie.echoes_of_the_elders.system.Raid.networking.s2c.CapabilitySync;
import com.bajookie.echoes_of_the_elders.util.EntityUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ServerNetworking {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(RequestCapabilitySync.TYPE, (packet, player, responseSender) -> {
            var world = player.getWorld();
            var entity = EntityUtil.getEntityByUUID(world, packet.entityUuid());

            if (entity instanceof LivingEntity livingEntity) {
                CapabilitySync.send(player, livingEntity);
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(RequestLeftClickAbilitySync.TYPE,(packet, player, responseSender) -> {
            World world = player.getWorld();
            ItemStack stack = packet.stack();
            ((ILeftClickAbility)stack.getItem()).performLeftClickAbility(stack,world,player);
        });
    }
}
