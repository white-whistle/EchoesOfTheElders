package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.entity.ModDamageSources;
import com.bajookie.echoes_of_the_elders.item.ILeftClickAbility;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSources;playerAttack(Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/entity/damage/DamageSource;"))
    private DamageSource playerAttackProxy(DamageSources instance, PlayerEntity attacker) {
        var stack = attacker.getMainHandStack();

        if (stack.isOf(ModItems.GODSLAYER)) {
            return ModDamageSources.divineAttack(attacker);
        }

        return instance.playerAttack(attacker);
    }
    @Inject(method = "getAttackCooldownProgress",at = @At("HEAD"),cancellable = true)
    private void getAttackCooldownProgress(float baseTime, CallbackInfoReturnable<Float> info) {
        if((((PlayerEntity)(Object)this)).hasStatusEffect(ModEffects.ECHO_HIT)){
            info.setReturnValue(1.0f);
        }
    }
    /* can work well if we need to do special attack
        @Inject(method = "attack",at = @At(value = "INVOKE",target = "Lnet/minecraft/enchantment/EnchantmentHelper;getSweepingMultiplier(Lnet/minecraft/entity/LivingEntity;)F"))
    public void attack(Entity target ,CallbackInfo info) {
        ItemStack artiStack = (((PlayerEntity)(Object)this)).getStackInHand(Hand.MAIN_HAND);
        if (artiStack != null && artiStack.getItem() instanceof ILeftClickAbility iLeftClickAbility) {
            iLeftClickAbility.performLeftClickAbility(artiStack,(((PlayerEntity)(Object)this)).getWorld(),(((PlayerEntity)(Object)this)));
        }
    }
     */
}
