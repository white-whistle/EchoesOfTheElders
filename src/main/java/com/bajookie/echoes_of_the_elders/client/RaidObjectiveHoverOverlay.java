package com.bajookie.echoes_of_the_elders.client;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;

public class RaidObjectiveHoverOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        var player = client.player;
        if (player == null) return;

        var target = client.targetedEntity;
        if (target == null) return;

        if (!(target instanceof LivingEntity livingEntity)) return;

        ModCapabilities.RAID_OBJECTIVE.use(livingEntity, (o) -> {
            if (!o.canAcceptKeyFromPlayer(player)) return;

            var centerX = client.getWindow().getScaledWidth() / 2;
            var centerY = client.getWindow().getScaledHeight() / 2;

            drawContext.drawItem(ModItems.OLD_KEY.getDefaultStack(), centerX + 4, centerY - 8, 0);
        });
    }
}

