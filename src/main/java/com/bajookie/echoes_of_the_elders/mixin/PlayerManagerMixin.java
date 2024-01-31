package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "respawnPlayer",at = @At("RETURN"))
    public void respawnPlayer(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> info){
        ServerPlayerEntity serverPlayer = info.getReturnValue();
        EntityAttributeInstance instanceHp = serverPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        EntityAttributeInstance instanceAttackSpeed = serverPlayer.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
        EntityAttributeInstance instanceSpeed = serverPlayer.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        EntityAttributeInstance instanceAttack = serverPlayer.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        instanceHp.setBaseValue(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue());
        instanceAttackSpeed.setBaseValue(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED).getBaseValue());
        instanceSpeed.setBaseValue(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getBaseValue());
        instanceAttack.setBaseValue(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue());
    }
}
