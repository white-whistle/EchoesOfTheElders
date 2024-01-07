package com.bajookie.echoes_of_the_elders.world.dimension;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Matrix4f;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModSkyRenderer implements DimensionRenderingRegistry.SkyRenderer {
    private static final Identifier SKY = new Identifier(MOD_ID,"textures/overlay/sky/lost.png");
    public ModSkyRenderer(){}
    private VertexBuffer starsBuffer;

    @Override
    public void render(WorldRenderContext context) {
        renderSkyBox(context.matrixStack());
        renderStars();
    }
    private void renderSkyBox(MatrixStack matrices){
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderTexture(0, SKY);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        for (int i = 0; i < 6; ++i) {
            matrices.push();
            if (i == 1) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
            }
            if (i == 2) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0f));
            }
            if (i == 3) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f));
            }
            if (i == 4) {
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0f));
            }
            if (i == 5) {
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90.0f));
            }
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0f, 0.0f).color(255, 255, 255, 255).next();
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0f, 16.0f).color(255, 255, 255, 255).next();
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(16.0f, 16.0f).color(255, 255, 255, 255).next();
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(16.0f, 0.0f).color(255, 255, 255, 255).next();
            tessellator.draw();
            matrices.pop();
        }
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    private void renderStars() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        if (this.starsBuffer != null) {
            this.starsBuffer.close();
        }
        this.starsBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.BuiltBuffer builtBuffer = this.renderStars(bufferBuilder);
        this.starsBuffer.bind();
        this.starsBuffer.upload(builtBuffer);
        VertexBuffer.unbind();
    }

    private BufferBuilder.BuiltBuffer renderStars(BufferBuilder buffer) {
        Random random = Random.create(10842L);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        for (int i = 0; i < 1500; ++i) {
            double d = random.nextFloat() * 2.0f - 1.0f;
            double e = random.nextFloat() * 2.0f - 1.0f;
            double f = random.nextFloat() * 2.0f - 1.0f;
            double g = 0.15f + random.nextFloat() * 0.1f;
            double h = d * d + e * e + f * f;
            if (!(h < 1.0) || !(h > 0.01)) continue;
            h = 1.0 / Math.sqrt(h);
            double j = (d *= h) * 100.0;
            double k = (e *= h) * 100.0;
            double l = (f *= h) * 100.0;
            double m = Math.atan2(d, f);
            double n = Math.sin(m);
            double o = Math.cos(m);
            double p = Math.atan2(Math.sqrt(d * d + f * f), e);
            double q = Math.sin(p);
            double r = Math.cos(p);
            double s = random.nextDouble() * Math.PI * 2.0;
            double t = Math.sin(s);
            double u = Math.cos(s);
            for (int v = 0; v < 4; ++v) {
                double ab;
                double w = 0.0;
                double x = (double)((v & 2) - 1) * g;
                double y = (double)((v + 1 & 2) - 1) * g;
                double z = 0.0;
                double aa = x * u - y * t;
                double ac = ab = y * u + x * t;
                double ad = aa * q + 0.0 * r;
                double ae = 0.0 * q - aa * r;
                double af = ae * n - ac * o;
                double ag = ad;
                double ah = ac * n + ae * o;
                buffer.vertex(j + af, k + ag, l + ah).next();
            }
        }
        return buffer.end();
    }


    private boolean hasBlindnessOrDarkness(Camera camera) {
        Entity entity = camera.getFocusedEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            return livingEntity.hasStatusEffect(StatusEffects.BLINDNESS) || livingEntity.hasStatusEffect(StatusEffects.DARKNESS);
        }
        return false;
    }

}
