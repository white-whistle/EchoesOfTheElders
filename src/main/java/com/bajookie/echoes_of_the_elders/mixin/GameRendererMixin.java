package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.entity.custom.TvArrowEntity;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    //@Redirect(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V"))
    protected void setPos(Camera camera, BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta) {
        if (focusedEntity instanceof PlayerEntity player ){
            if (ModCapabilities.SCREEN_SWITCH_OBJECTIVE.hasCapability(player)){
                ModCapabilities.SCREEN_SWITCH_OBJECTIVE.use(player,(screenSwitchCapability -> {
                    if (screenSwitchCapability.getTargetScreen(player.getWorld()) != null){
                        camera.update(area,screenSwitchCapability.getTargetScreen(player.getWorld()),thirdPerson,inverseView,tickDelta);
                    } else {
                        camera.update(area,focusedEntity,thirdPerson,inverseView,tickDelta);
                    }
                }));
            } else {
                camera.update(area, focusedEntity,thirdPerson,inverseView, tickDelta);
            }

        } else {
            camera.update(area, focusedEntity,thirdPerson,inverseView, tickDelta);
        }
    }
}