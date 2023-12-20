package com.bajookie.echoes_of_the_elders.mixin;


import com.bajookie.echoes_of_the_elders.client.CustomOutlineColor;
import com.bajookie.echoes_of_the_elders.client.EntityEffectRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow
    private static void drawCuboidShapeOutline(MatrixStack matrices, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ, float red, float green, float blue, float alpha) {
    }

    @Shadow
    private @Nullable ClientWorld world;

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

        if (customColor != null) {
            drawCuboidShapeOutline(matrices, vertexConsumer, state.getOutlineShape(this.world, pos, ShapeContext.of(entity)), (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ, customColor.getRedF(), customColor.getGreenF(), customColor.getBlueF(), 0.4f);
            ci.cancel();
        }
    }
}