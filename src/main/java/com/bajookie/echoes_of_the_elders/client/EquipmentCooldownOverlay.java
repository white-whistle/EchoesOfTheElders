package com.bajookie.echoes_of_the_elders.client;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.ability.IHasSlotAbility;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;

public class EquipmentCooldownOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        var player = client.player;
        if (player == null) return;


        var matrix = drawContext.getMatrices();
        float scale = 0.5f;
        matrix.push();
        matrix.scale(scale, scale, 1);

        int renderIndex = 0;
        for (var slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;

            var stack = player.getEquippedStack(slot);
            if (!(stack.getItem() instanceof IArtifact)) continue;

            if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) continue;

            var centerX = client.getWindow().getScaledWidth() / 2;
            var centerY = client.getWindow().getScaledHeight() / 2;

            var x = (int) ((centerX - 20 - (8 * renderIndex)) / scale);
            var y = (int) ((centerY + 12) / scale);
            
            drawContext.drawItem(stack, x, y, 0);
            drawContext.drawItemInSlot(client.textRenderer, stack, x, y);

            renderIndex++;
        }

        matrix.pop();


    }
}
