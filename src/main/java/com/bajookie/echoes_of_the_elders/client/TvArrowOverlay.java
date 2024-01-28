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
                            RenderSystem.disableDepthTest();
                            RenderSystem.depthMask(false);
                            RenderSystem.enableBlend();
                            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
                            drawContext.setShaderColor(42/255f, 9/255f, 227f, 0.01f);
                            drawContext.drawTexture(VIGNETTE_TEXTURE, 0, 0, -90, 0.0f, 0.0f, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
                            RenderSystem.depthMask(true);
                            RenderSystem.enableDepthTest();
                            drawContext.setShaderColor(1.0f, 1.0f, 1.0f, 1f);
                            RenderSystem.disableBlend();
                            RenderSystem.defaultBlendFunc();
                        }
                    }));
                }
            }
        }
    }
}

