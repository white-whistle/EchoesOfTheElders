package com.bajookie.echoes_of_the_elders.client;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class TvArrowOverlay implements HudRenderCallback {
    private static final Identifier VIGNETTE_TEXTURE = new Identifier("textures/misc/vignette.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            x = client.getWindow().getScaledWidth() / 2;
            y = client.getWindow().getScaledHeight() / 2;
            if (client.player != null) {
                if (ModCapabilities.SCREEN_SWITCH_OBJECTIVE.hasCapability(client.player)){
                    ModCapabilities.SCREEN_SWITCH_OBJECTIVE.use(client.player, (screenSwitchCapability -> {
                        if (screenSwitchCapability.getTargetScreen(client.world) != null) {
                        }
                    }));
                }
            }
        }
    }
}

