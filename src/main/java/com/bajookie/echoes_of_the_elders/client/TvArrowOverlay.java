package com.bajookie.echoes_of_the_elders.client;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class TvArrowOverlay implements HudRenderCallback {
    private static final Identifier STOPWATCH_00 = new Identifier(MOD_ID, "textures/overlay/stasis/stasis_stopwatch.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            x = client.getWindow().getScaledWidth() / 2;
            y = client.getWindow().getScaledHeight() / 2;
            if (client.player != null) {
                if (client.player.getMainHandStack().getItem() == ModItems.TV_ARROW) {
                    client.getCameraEntity();
                }
            }
        }
    }
}
