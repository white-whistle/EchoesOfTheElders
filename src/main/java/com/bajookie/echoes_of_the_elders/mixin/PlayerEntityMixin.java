package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.entity.ModDamageSources;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSources;playerAttack(Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/entity/damage/DamageSource;"))
    public DamageSource playerAttackProxy(DamageSources instance, PlayerEntity attacker) {
        var stack = attacker.getMainHandStack();

        if (stack.isOf(ModItems.GODSLAYER)) {
            return ModDamageSources.divineAttack(attacker);
        }

        return instance.playerAttack(attacker);
    }
}
