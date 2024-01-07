package com.bajookie.echoes_of_the_elders.mixin;


import com.bajookie.echoes_of_the_elders.client.CustomOutlineColor;
import com.bajookie.echoes_of_the_elders.client.EntityEffectRenderer;
import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow
    private static void drawCuboidShapeOutline(MatrixStack matrices, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ, float red, float green, float blue, float alpha) {
    }

    @Shadow
    private @Nullable ClientWorld world;
    @Shadow
    private @Nullable VertexBuffer lightSkyBuffer;
    @Shadow
    private @Final MinecraftClient client;
    @Shadow
    private @Nullable VertexBuffer starsBuffer;
    @Shadow
    private @Nullable VertexBuffer darkSkyBuffer;
    @Unique
    private static final Identifier SUN = new Identifier(MOD_ID,"textures/overlay/sky/sun.png");
    @Unique
    private static final Identifier MOON_PHASES = new Identifier(MOD_ID,"textures/overlay/sky/moons.png");



    @Inject(method = "renderEntity", at = @At(value = "RETURN"))
    private void renderEntity(Entity entity, double x, double y, double z, float g,
                              MatrixStack matrix, VertexConsumerProvider v, CallbackInfo info) {
        if (entity instanceof LivingEntity) {
            EntityEffectRenderer.prepareRenderInWorld((LivingEntity) entity);
        }
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void render(MatrixStack matrices, float tickDelta, long limitTime,
                        boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer,
                        LightmapTextureManager lightmapTextureManager, Matrix4f matrix, CallbackInfo info) {
        EntityEffectRenderer.renderInWorld(matrices, camera);
    }

    @Inject(method = "drawBlockOutline", at = @At("HEAD"), cancellable = true)
    private void drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci) {
        var customColor = CustomOutlineColor.getOutlineColor();

        if (customColor != null && this.world != null) {
            drawCuboidShapeOutline(matrices, vertexConsumer, state.getOutlineShape(this.world, pos, ShapeContext.of(entity)), (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ, customColor.getRedF(), customColor.getGreenF(), customColor.getBlueF(), 0.4f);
            ci.cancel();
        }
    }

    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getDimensionEffects()Lnet/minecraft/client/render/DimensionEffects;", ordinal = 1), cancellable = true)
    public void renderSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean thickFog, Runnable fogCallback, CallbackInfo info) {
        if (this.client.world.getDimensionKey() == ModDimensions.DEFENSE_DIM_TYPE) {
            float q;
            float p;
            float o;
            int m;
            float k;
            float i;
            Vec3d vec3d = this.world.getSkyColor(this.client.gameRenderer.getCamera().getPos(), tickDelta); //TODO create a custom function for sky color and understand how it works
            float f = (float)vec3d.x;
            float g = (float)vec3d.y;
            float h = (float)vec3d.z;
            BackgroundRenderer.applyFogColor();
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            RenderSystem.depthMask(false);
            RenderSystem.setShaderColor(f, g, h, 1.0f);
            ShaderProgram shaderProgram = RenderSystem.getShader();
            this.lightSkyBuffer.bind();
            this.lightSkyBuffer.draw(matrices.peek().getPositionMatrix(), projectionMatrix, shaderProgram);
            VertexBuffer.unbind();
            RenderSystem.enableBlend();
            float[] fs = this.world.getDimensionEffects().getFogColorOverride(this.world.getSkyAngle(tickDelta), tickDelta);
            if (fs != null) {
                RenderSystem.setShader(GameRenderer::getPositionColorProgram);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
                i = MathHelper.sin(this.world.getSkyAngleRadians(tickDelta)) < 0.0f ? 180.0f : 0.0f;
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0f));
                float j = fs[0];
                k = fs[1];
                float l = fs[2];
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
                bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(j, k, l, fs[3]).next();
                m = 16;
                for (int n = 0; n <= 16; ++n) {
                    o = (float)n * ((float)Math.PI * 2) / 16.0f;
                    p = MathHelper.sin(o);
                    q = MathHelper.cos(o);
                    bufferBuilder.vertex(matrix4f, p * 120.0f, q * 120.0f, -q * 40.0f * fs[3]).color(fs[0], fs[1], fs[2], 0.0f).next();
                }
                BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
                matrices.pop();
            }
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            matrices.push();
            i = 1.0f - this.world.getRainGradient(tickDelta);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, i);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.world.getSkyAngle(tickDelta) * 360.0f));
            Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();
            k = 30.0f;
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, SUN);
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferBuilder.vertex(matrix4f2, -k, 100.0f, -k).texture(0.0f, 0.0f).next();
            bufferBuilder.vertex(matrix4f2, k, 100.0f, -k).texture(1.0f, 0.0f).next();
            bufferBuilder.vertex(matrix4f2, k, 100.0f, k).texture(1.0f, 1.0f).next();
            bufferBuilder.vertex(matrix4f2, -k, 100.0f, k).texture(0.0f, 1.0f).next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
            k = 20.0f;
            RenderSystem.setShaderTexture(0, MOON_PHASES);
            int r = this.world.getMoonPhase(); // u fro uv ?
            int s = r % 4;
            m = r / 4 % 2;
            float t = (float)(s + 0) / 4.0f;
            o = (float)(m + 0) / 2.0f;
            p = (float)(s + 1) / 4.0f;
            q = (float)(m + 1) / 2.0f;
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferBuilder.vertex(matrix4f2, -k, -50.0f, k).texture(p, q).next(); //-50 is for size, - cuse its opposite?
            bufferBuilder.vertex(matrix4f2, k, -50.0f, k).texture(t, q).next();
            bufferBuilder.vertex(matrix4f2, k, -50.0f, -k).texture(t, o).next();
            bufferBuilder.vertex(matrix4f2, -k, -50.0f, -k).texture(p, o).next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
            float u = this.world.method_23787(tickDelta) * i;
            if (u > 0.0f) {
                RenderSystem.setShaderColor(u, u, u, u);
                BackgroundRenderer.clearFog();
                this.starsBuffer.bind();
                this.starsBuffer.draw(matrices.peek().getPositionMatrix(), projectionMatrix, GameRenderer.getPositionProgram());
                VertexBuffer.unbind();
                fogCallback.run();
            }
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            matrices.pop();
            RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);
            double d = this.client.player.getCameraPosVec((float)tickDelta).y - this.world.getLevelProperties().getSkyDarknessHeight(this.world);
            if (d < 0.0) {
                matrices.push();
                matrices.translate(0.0f, 12.0f, 0.0f);
                this.darkSkyBuffer.bind();
                this.darkSkyBuffer.draw(matrices.peek().getPositionMatrix(), projectionMatrix, shaderProgram);
                VertexBuffer.unbind();
                matrices.pop();
            }
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.depthMask(true);
            info.cancel();
        }
    }


}