package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ILeftClickAbility;
import com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s.RequestLeftClickAbilitySync;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;


    @Inject(method = "doAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;hasLimitedAttackSpeed()Z", ordinal = 1), cancellable = true)
    private void doAttackInject(CallbackInfoReturnable<Boolean> info) {
        if (player == null) info.cancel();
        ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
        if (itemStack != null && itemStack.getItem() instanceof ILeftClickAbility) {
            player.swingHand(Hand.MAIN_HAND);
            if (player.getAttackCooldownProgress(0.5f) == 1f) {
                RequestLeftClickAbilitySync.send(itemStack);
            }else {
                info.setReturnValue(false);
            }
        }
    }

}
