package com.bajookie.echoes_of_the_elders.system.Raid.client;

import com.bajookie.echoes_of_the_elders.client.SpriteUV;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class RaidEntityOverlayRenderer {

    private static final float MAX_DISTANCE = 40;
    private static final Identifier RAID_ICON_ATLAS = new Identifier(MOD_ID, "textures/overlay/raid_atlas.png");

    private static final SpriteUV RAID_DEFEND = new SpriteUV(0, 0, 0.5f, 1f);
    private static final SpriteUV RAID_ENEMY = new SpriteUV(0.5f, 0, 1f, 1f);

    private static final float FULL_SIZE = 18;

    private static final List<LivingEntity> renderedEntities = new ArrayList<>();

    public static void prepareRenderInWorld(LivingEntity entity) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (entity.isDead()) return;

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
        Matrix4f m4f = matrix.peek().getPositionMatrix();

        // ModCapabilities.RAID_OBJECTIVE.use(entity, o -> {
        //     if (!o.active) return;
        //     drawStatusEffect(m4f, x, y, FULL_SIZE, RAID_DEFEND);
        // });

        ModCapabilities.RAID_ENEMY.use(entity, o -> {
            drawStatusEffect(m4f, x, y, FULL_SIZE, RAID_ENEMY);
        });
    }


    private static void drawStatusEffect(Matrix4f matrix4f, double x, double y, float size, SpriteUV spriteUV) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, RAID_ICON_ATLAS);
        RenderSystem.enableBlend();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        float offsetX = -size / 2;
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

        var u = spriteUV.minU();
        var v = spriteUV.minV();
        var mu = spriteUV.maxU();
        var mv = spriteUV.maxV();

        buffer.vertex(matrix4f, (float) (x + offsetX), (float) y, 0)
                .texture(u, v).next();
        buffer.vertex(matrix4f, (float) (x + offsetX), (float) (y + size), 0)
                .texture(u, mv).next();
        buffer.vertex(matrix4f, (float) (x + offsetX + size), (float) (y + size), 0)
                .texture(mu, mv).next();
        buffer.vertex(matrix4f, (float) (x + offsetX + size), (float) y, 0)
                .texture(mu, v).next();

        tessellator.draw();
    }
}