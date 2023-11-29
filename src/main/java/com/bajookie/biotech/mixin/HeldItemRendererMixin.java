package com.bajookie.biotech.mixin;

import com.bajookie.biotech.client.animation.AnimationUtil;
import com.bajookie.biotech.item.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Shadow
    protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

    @Shadow
    protected abstract void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/math/MathHelper;sin(F)F", shift = At.Shift.BEFORE, ordinal = 6), cancellable = true)
    public void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {

        boolean isMainHand = hand == Hand.MAIN_HAND;
        Arm arm = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
        var isRightArm = arm == Arm.RIGHT;

        if (item.isOf(ModItems.VITALITY_PUMP)) {

            var animationProgress = AnimationUtil.VITALITY_PUMP_HEARTBEAT_ANIMATION.getProgress(tickDelta);
            var inAnimation = AnimationUtil.VITALITY_PUMP_HEARTBEAT_ANIMATION.isActive();

            // default animations
            this.applyEquipOffset(matrices, arm, inAnimation ? 0: equipProgress);
            this.applySwingOffset(matrices, arm, inAnimation ? 0 : swingProgress);


            // ambient animation
            var ambientAnimationProgress = AnimationUtil.VITALITY_PUMP_HEARTBEAT_AMBIENT_ANIMATION.getProgress(tickDelta);
            var p = 1 + (AnimationUtil.sin(ambientAnimationProgress) * 0.04f);
            matrices.scale(1, p, p);

            // item animation
            var invertedProgress = 1 - animationProgress;
            matrices.scale(1 - (0.2f * invertedProgress),1 + (0.4f * invertedProgress),1 - (0.4f * invertedProgress));

            // render item
            ((HeldItemRenderer)(Object)(this)).renderItem(player, item, isRightArm ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND, !isRightArm, matrices, vertexConsumers, light);
            matrices.pop();

            // exit from injected method early
            ci.cancel();
        }
    }
}
