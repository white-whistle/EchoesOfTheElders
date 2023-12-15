package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.IPlayerBoundItemCooldownManager;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "createCooldownManager", at = @At("RETURN"))
    public void getItemCooldownManager(CallbackInfoReturnable<ItemCooldownManager> cir) {
        var itemCooldownManager = cir.getReturnValue();

        var player = (PlayerEntity) (Object) this;

        ((IPlayerBoundItemCooldownManager) itemCooldownManager).eote$setPlayerEntity(player);
    }
}
