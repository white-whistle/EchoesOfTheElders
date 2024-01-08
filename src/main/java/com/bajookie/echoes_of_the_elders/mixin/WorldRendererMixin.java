package com.bajookie.echoes_of_the_elders.mixin;


import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.client.CustomOutlineColor;
import com.bajookie.echoes_of_the_elders.client.EntityEffectRenderer;
import com.bajookie.echoes_of_the_elders.world.biome.ModBiomes;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
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
    private int ticks;
    @Final
    @Shadow
    private float[] NORMAL_LINE_DX;
    @Final
    @Shadow
    private float[] NORMAL_LINE_DZ;

    @Shadow
    private @Nullable VertexBuffer starsBuffer;
    @Shadow
    private @Nullable VertexBuffer darkSkyBuffer;
    @Unique
    private static final Identifier MOD_RAIN = new Identifier(MOD_ID,"textures/overlay/sky/light_rain.png");

    @Unique
    private static final Identifier MOD_SNOW = new Identifier(MOD_ID,"textures/overlay/sky/snow.png");
    @Unique
    private static final Identifier MOD_SUN = new Identifier(MOD_ID, "textures/overlay/sky/sun.png");
    @Unique
    private static final Identifier MOD_MOON_PHASES = new Identifier(MOD_ID, "textures/overlay/sky/moons.png");


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
            //Vec3d vec3d = this.world.getSkyColor(this.client.gameRenderer.getCamera().getPos(), tickDelta);
            double skyR =237;
            double skyG=83;
            double skyB=17;
            Vec3d vec3d = new Vec3d(skyR/255,skyG/255,skyB/255);
            float f = (float) vec3d.x;
            float g = (float) vec3d.y;
            float h = (float) vec3d.z;
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
            if (fs == null){
                fs = new float[]{207f/255f, 10f/255f, 10f/255f,130f/255f};//R,G,B,A
            }
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
                bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(j, k, l, fs[3]).next(); // this colors the sky top
                m = 16;
                for (int n = 0; n <= 16; ++n) {
                    o = (float) n * ((float) Math.PI * 2) / 16.0f;
                    p = MathHelper.sin(o);
                    q = MathHelper.cos(o);
                    bufferBuilder.vertex(matrix4f, p * 120.0f, q * 120.0f, -q * 40.0f * fs[3]).color(fs[0], fs[1], fs[2], 0.0f).next();//looks like sky top glare
                }
                BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
                matrices.pop();
            }
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            matrices.push();
            i = 1.0f - this.world.getRainGradient(tickDelta);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, i);// i dont know what that is
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.world.getSkyAngle(tickDelta) * 360.0f));
            Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();
            k = 30.0f;
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, MOD_SUN);
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferBuilder.vertex(matrix4f2, -k, 100.0f, -k).texture(0.0f, 0.0f).next();
            bufferBuilder.vertex(matrix4f2, k, 100.0f, -k).texture(1.0f, 0.0f).next();
            bufferBuilder.vertex(matrix4f2, k, 100.0f, k).texture(1.0f, 1.0f).next();
            bufferBuilder.vertex(matrix4f2, -k, 100.0f, k).texture(0.0f, 1.0f).next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
            k = 20.0f;
            RenderSystem.setShaderTexture(0, MOD_MOON_PHASES);
            int r = this.world.getMoonPhase(); // u for uv ?
            int s = r % 4;
            m = r / 4 % 2;
            float t = (float) (s + 0) / 4.0f;
            o = (float) (m + 0) / 2.0f;
            p = (float) (s + 1) / 4.0f;
            q = (float) (m + 1) / 2.0f;
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferBuilder.vertex(matrix4f2, -k, -100.0f, k).texture(p, q).next(); //-50 is for size, - cuse its opposite?
            bufferBuilder.vertex(matrix4f2, k, -100.0f, k).texture(t, q).next();
            bufferBuilder.vertex(matrix4f2, k, -100.0f, -k).texture(t, o).next();
            bufferBuilder.vertex(matrix4f2, -k, -100.0f, -k).texture(p, o).next();
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
            double d = this.client.player.getCameraPosVec((float) tickDelta).y - this.world.getLevelProperties().getSkyDarknessHeight(this.world);
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

    @Inject(method = "renderWeather", at = @At("HEAD"),cancellable = true)
    private void renderWeather(LightmapTextureManager manager, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo info) {
        if (this.client.player.getWorld().getBiome(this.client.player.getBlockPos()).getKey().get() == ModBiomes.LOST_BIOME){
            float f = this.client.world.getRainGradient(tickDelta);
            if (f <= 0.0f) {
                return;
            }
            manager.enable();
            ClientWorld world = this.client.world;
            int i = MathHelper.floor(cameraX);
            int j = MathHelper.floor(cameraY);
            int k = MathHelper.floor(cameraZ);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            int l = 5;
            if (MinecraftClient.isFancyGraphicsOrBetter()) {
                l = 10;
            }
            RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
            int m = -1;
            float g = (float) this.ticks + tickDelta;
            RenderSystem.setShader(GameRenderer::getParticleProgram);
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int n = k - l; n <= k + l; ++n) {
                for (int o = i - l; o <= i + l; ++o) {
                    float y;
                    float h;
                    int t;
                    int p = (n - k + 16) * 32 + o - i + 16;
                    double d = (double) this.NORMAL_LINE_DX[p] * 0.5;
                    double e = (double) this.NORMAL_LINE_DZ[p] * 0.5;
                    mutable.set((double) o, cameraY, (double) n);
                    Biome biome = world.getBiome(mutable).value();
                    if (!biome.hasPrecipitation()) continue;
                    int q = world.getTopY(Heightmap.Type.MOTION_BLOCKING, o, n);
                    int r = j - l;
                    int s = j + l;
                    if (r < q) {
                        r = q;
                    }
                    if (s < q) {
                        s = q;
                    }
                    if ((t = q) < j) {
                        t = j;
                    }
                    if (r == s) continue;
                    Random random = Random.create(o * o * 3121 + o * 45238971 ^ n * n * 418711 + n * 13761);
                    mutable.set(o, r, n);
                    Biome.Precipitation precipitation = biome.getPrecipitation(mutable);
                    if (precipitation == Biome.Precipitation.RAIN) {
                        if (m != 0) {
                            if (m >= 0) {
                                tessellator.draw();
                            }
                            m = 0;
                            RenderSystem.setShaderTexture(0, MOD_RAIN);
                            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                        }
                        int u = this.ticks + o * o * 3121 + o * 45238971 + n * n * 418711 + n * 13761 & 0x1F;
                        h = -((float) u + tickDelta) / 32.0f * (3.0f + random.nextFloat());
                        double v = (double) o + 0.5 - cameraX;
                        double w = (double) n + 0.5 - cameraZ;
                        float x = (float) Math.sqrt(v * v + w * w) / (float) l;
                        y = ((1.0f - x * x) * 0.5f + 0.5f) * f;
                        mutable.set(o, t, n);
                        int z = WorldRenderer.getLightmapCoordinates(world, mutable);
                        bufferBuilder.vertex((double) o - cameraX - d + 0.5, (double) s - cameraY, (double) n - cameraZ - e + 0.5).texture(0.0f, (float) r * 0.25f + h).color(1.0f, 1.0f, 1.0f, y).light(z).next();
                        bufferBuilder.vertex((double) o - cameraX + d + 0.5, (double) s - cameraY, (double) n - cameraZ + e + 0.5).texture(1.0f, (float) r * 0.25f + h).color(1.0f, 1.0f, 1.0f, y).light(z).next();
                        bufferBuilder.vertex((double) o - cameraX + d + 0.5, (double) r - cameraY, (double) n - cameraZ + e + 0.5).texture(1.0f, (float) s * 0.25f + h).color(1.0f, 1.0f, 1.0f, y).light(z).next();
                        bufferBuilder.vertex((double) o - cameraX - d + 0.5, (double) r - cameraY, (double) n - cameraZ - e + 0.5).texture(0.0f, (float) s * 0.25f + h).color(1.0f, 1.0f, 1.0f, y).light(z).next();
                        continue;
                    }
                    if (precipitation != Biome.Precipitation.SNOW) continue;
                    if (m != 1) {
                        if (m >= 0) {
                            tessellator.draw();
                        }
                        m = 1;
                        RenderSystem.setShaderTexture(0, MOD_SNOW);
                        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                    }
                    float aa = -((float) (this.ticks & 0x1FF) + tickDelta) / 512.0f;
                    h = (float) (random.nextDouble() + (double) g * 0.01 * (double) ((float) random.nextGaussian()));
                    float ab = (float) (random.nextDouble() + (double) (g * (float) random.nextGaussian()) * 0.001);
                    double ac = (double) o + 0.5 - cameraX;
                    double ad = (double) n + 0.5 - cameraZ;
                    y = (float) Math.sqrt(ac * ac + ad * ad) / (float) l;
                    float ae = ((1.0f - y * y) * 0.3f + 0.5f) * f;
                    mutable.set(o, t, n);
                    int af = WorldRenderer.getLightmapCoordinates(world, mutable);
                    int ag = af >> 16 & 0xFFFF;
                    int ah = af & 0xFFFF;
                    int ai = (ag * 3 + 240) / 4;
                    int aj = (ah * 3 + 240) / 4;
                    bufferBuilder.vertex((double) o - cameraX - d + 0.5, (double) s - cameraY, (double) n - cameraZ - e + 0.5).texture(0.0f + h, (float) r * 0.25f + aa + ab).color(1.0f, 1.0f, 1.0f, ae).light(aj, ai).next();
                    bufferBuilder.vertex((double) o - cameraX + d + 0.5, (double) s - cameraY, (double) n - cameraZ + e + 0.5).texture(1.0f + h, (float) r * 0.25f + aa + ab).color(1.0f, 1.0f, 1.0f, ae).light(aj, ai).next();
                    bufferBuilder.vertex((double) o - cameraX + d + 0.5, (double) r - cameraY, (double) n - cameraZ + e + 0.5).texture(1.0f + h, (float) s * 0.25f + aa + ab).color(1.0f, 1.0f, 1.0f, ae).light(aj, ai).next();
                    bufferBuilder.vertex((double) o - cameraX - d + 0.5, (double) r - cameraY, (double) n - cameraZ - e + 0.5).texture(0.0f + h, (float) s * 0.25f + aa + ab).color(1.0f, 1.0f, 1.0f, ae).light(aj, ai).next();
                }
            }
            if (m >= 0) {
                tessellator.draw();
            }
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            manager.disable();
            info.cancel();
        }
    }


}