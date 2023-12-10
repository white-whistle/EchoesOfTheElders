package com.bajookie.echoes_of_the_elders.client;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityEffectRenderer {

    private static final float MAX_DISTANCE = 40;
    private static final Identifier STATUS_EFFECT_ATLAS = new Identifier("textures/atlas/mob_effects.png");

    private static final float FULL_SIZE = 8;

    private static final List<LivingEntity> renderedEntities = new ArrayList<>();

    public static void prepareRenderInWorld(LivingEntity entity) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (entity.distanceTo(client.getCameraEntity()) > MAX_DISTANCE) {
            return;
        }

        renderedEntities.add(entity);

    }

    public static void renderInWorld(MatrixStack matrix, Camera camera) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (camera == null) {
            camera = client.getEntityRenderDispatcher().camera;
        }

        if (camera == null) {
            renderedEntities.clear();
            return;
        }

        if (renderedEntities.isEmpty()) {
            return;
        }

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE,
                GL11.GL_ZERO);

        for (LivingEntity entity : renderedEntities) {

            float scaleToGui = 0.025f;
            boolean sneaking = entity.isInSneakingPose();
            float height = entity.getHeight() + 0.6F - (sneaking ? 0.25F : 0.0F);

            float tickDelta = client.getTickDelta();
            double x = MathHelper.lerp((double) tickDelta, entity.prevX, entity.getX());
            double y = MathHelper.lerp((double) tickDelta, entity.prevY, entity.getY());
            double z = MathHelper.lerp((double) tickDelta, entity.prevZ, entity.getZ());

            Vec3d camPos = camera.getPos();
            double camX = camPos.x;
            double camY = camPos.y;
            double camZ = camPos.z;


            matrix.push();
            matrix.translate(x - camX, (y + height) - camY, z - camZ);
            matrix.multiply(new Quaternionf().rotationY((float) Math.toRadians(-camera.getYaw())));
            matrix.multiply(new Quaternionf().rotationX((float) Math.toRadians(camera.getPitch())));
            matrix.scale(-scaleToGui, -scaleToGui, scaleToGui);

            render(matrix, entity, 0, 0);

            matrix.pop();
        }

        RenderSystem.disableBlend();

        renderedEntities.clear();
    }

    public static void render(MatrixStack matrix, LivingEntity entity, double x, double y) {

        // Relation relation = EntityUtil.determineRelation(entity);
        //
        // int outlineColor = relation.equals(Relation.FRIEND) ? (
        //             0x00FF00
        //         ): (
        //         relation.equals(Relation.FOE) ? (
        //                 0xFF0000
        //                 ): (
        //                 0xACACAC
        //                 )
        //         );

        // Matrix4f m4f = matrix.peek().getPositionMatrix(); //  .getModel();
        //
        // var statusEffects = entity.getStatusEffects();
        //
        // drawStatusEffect(m4f, x, y, FULL_SIZE, statusEffects);
    }


    private static void drawStatusEffect(Matrix4f matrix4f, double x, double y, float size, Collection<StatusEffectInstance> effects) {
        var client = MinecraftClient.getInstance();

        StatusEffectSpriteManager statusEffectSpriteManager = client.getStatusEffectSpriteManager();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, STATUS_EFFECT_ATLAS);
        RenderSystem.enableBlend();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        float offsetX = - effects.size() * size / 2;


        for (StatusEffectInstance statusEffectInstance : Ordering.natural().reverse().sortedCopy(effects)) {
            StatusEffect statusEffect = statusEffectInstance.getEffectType();
            if (!statusEffectInstance.shouldShowIcon()) continue;

            Sprite sprite = statusEffectSpriteManager.getSprite(statusEffect);

            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

            var u = sprite.getMinU();
            var v = sprite.getMinV();
            var mu = sprite.getMaxU();
            var mv = sprite.getMaxV();

            buffer.vertex(matrix4f, (float) (x + offsetX), (float) y, 0)
                    .texture(u, v).next();
            buffer.vertex(matrix4f, (float) (x + offsetX), (float) (y + size), 0)
                    .texture(u, mv).next();
            buffer.vertex(matrix4f, (float) (x + offsetX + size), (float) (y + size), 0)
                    .texture(mu, mv).next();
            buffer.vertex(matrix4f, (float) (x + offsetX + size), (float) y, 0)
                    .texture(mu, v).next();

            offsetX += size;

            tessellator.draw();
        }
    }
}