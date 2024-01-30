package com.bajookie.echoes_of_the_elders.events;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Stack;

public class PlayerAttackHandler implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (player.getInventory().contains(ModItems.ECHOING_SWORD.getDefaultStack())){
            ItemStack stack = player.getInventory().getStack(player.getInventory().getSlotWithStack(ModItems.ECHOING_SWORD.getDefaultStack()));
            if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())&& stack.getItem() instanceof IHasCooldown iHasCooldown) {
                player.addStatusEffect(new StatusEffectInstance(ModEffects.ECHO_HIT,40,1,true,false,false));
                player.getItemCooldownManager().set(stack.getItem(),iHasCooldown.getCooldown(stack));
            } else {
                if (player.hasStatusEffect(ModEffects.ECHO_HIT)){
                    player.removeStatusEffect(ModEffects.ECHO_HIT);
                }
            }
        }
        return ActionResult.PASS;
    }
}
