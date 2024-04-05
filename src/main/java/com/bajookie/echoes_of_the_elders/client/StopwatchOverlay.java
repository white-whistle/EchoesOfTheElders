package com.bajookie.echoes_of_the_elders.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class StopwatchOverlay implements HudRenderCallback {
    private static final Identifier STOPWATCH_00 = new Identifier(MOD_ID, "textures/overlay/stasis/stasis_stopwatch.png");
    private static final Identifier STOPWATCH_01 = new Identifier(MOD_ID, "textures/overlay/stasis/stasis_stopwatch_01.png");
    private static final Identifier STOPWATCH_02 = new Identifier(MOD_ID, "textures/overlay/stasis/stasis_stopwatch_02.png");
    private static final Identifier STOPWATCH_03 = new Identifier(MOD_ID, "textures/overlay/stasis/stasis_stopwatch_03.png");
    private static final Identifier STOPWATCH_04 = new Identifier(MOD_ID, "textures/overlay/stasis/stasis_stopwatch_04.png");
    private static final Identifier STOPWATCH_05 = new Identifier(MOD_ID, "textures/overlay/stasis/stasis_stopwatch_05.png");
    private static final Identifier STOPWATCH_06 = new Identifier(MOD_ID, "textures/overlay/stasis/stasis_stopwatch_06.png");
    private static final Identifier STOPWATCH_07 = new Identifier(MOD_ID, "textures/overlay/stasis/stasis_stopwatch_07.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        // int x = 0;
        // int y = 0;
        // MinecraftClient client = MinecraftClient.getInstance();
        // if (client != null) {
        //     x = client.getWindow().getScaledWidth() / 2;
        //     y = client.getWindow().getScaledHeight() / 2;
        //     if (client.player != null) {
        //         if (client.player.getMainHandStack().getItem() == ModItems.STASIS_STOPWATCH) {
        //             ItemStack stack = client.player.getMainHandStack();
        //             NbtCompound nbt = stack.getNbt();
        //             int overIndex = 0;
        //             if (nbt !=null){
        //                 int ticks = nbt.getInt("used_tick");
        //                 if (ticks ==0) return;
        //                 if (ticks <=20) overIndex=1;
        //                 else if (ticks <=40) overIndex=2;
        //                 else if (ticks <=60) overIndex=3;
        //                 else if (ticks <=80) overIndex=4;
        //                 else if (ticks <=100) overIndex=5;
        //                 else if (ticks <=120) overIndex=6;
        //                 else if (ticks <=140) overIndex=7;
        //             }
        //             RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        //             RenderSystem.setShaderColor(1.0f, 1f, 1.0f, 1.0f);
        //             RenderSystem.setShaderTexture(0, STOPWATCH_00);
        //             RenderSystem.setShaderTexture(0, STOPWATCH_01);
        //             RenderSystem.setShaderTexture(0, STOPWATCH_02);
        //             RenderSystem.setShaderTexture(0, STOPWATCH_03);
        //             RenderSystem.setShaderTexture(0, STOPWATCH_04);
        //             RenderSystem.setShaderTexture(0, STOPWATCH_05);
        //             RenderSystem.setShaderTexture(0, STOPWATCH_06);
        //             RenderSystem.setShaderTexture(0, STOPWATCH_07);
        //             switch (overIndex) {
        //                 case 0 -> drawContext.drawTexture(STOPWATCH_00, x - 10, y - 50, 0, 0, 32, 32, 32, 32);
        //                 case 1 -> drawContext.drawTexture(STOPWATCH_01, x - 10, y - 50, 0, 0, 32, 32, 32, 32);
        //                 case 2 -> drawContext.drawTexture(STOPWATCH_02, x - 10, y - 50, 0, 0, 32, 32, 32, 32);
        //                 case 3 -> drawContext.drawTexture(STOPWATCH_03, x - 10, y - 50, 0, 0, 32, 32, 32, 32);
        //                 case 4 -> drawContext.drawTexture(STOPWATCH_04, x - 10, y - 50, 0, 0, 32, 32, 32, 32);
        //                 case 5 -> drawContext.drawTexture(STOPWATCH_05, x - 10, y - 50, 0, 0, 32, 32, 32, 32);
        //                 case 6 -> drawContext.drawTexture(STOPWATCH_06, x - 10, y - 50, 0, 0, 32, 32, 32, 32);
        //                 case 7 -> drawContext.drawTexture(STOPWATCH_07, x - 10, y - 50, 0, 0, 32, 32, 32, 32);
        //             }
        //         }
        //     }
        // }
    }
}

