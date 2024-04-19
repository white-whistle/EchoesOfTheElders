package com.bajookie.echoes_of_the_elders.events;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.util.InventoryUtil;
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
        var oEchoStack = InventoryUtil.toStream(player.getInventory()).filter(stack -> stack.isOf(ModItems.ECHOING_SWORD)).findFirst();
        if (oEchoStack.isEmpty()) return ActionResult.PASS;

        var stack = oEchoStack.get();
        var item = stack.getItem();
        var cdm = player.getItemCooldownManager();

        if (!cdm.isCoolingDown(item) && item instanceof IHasCooldown iHasCooldown) {
            player.addStatusEffect(new StatusEffectInstance(ModEffects.ECHO_HIT, 40, 1, true, false, false));
            cdm.set(stack.getItem(), iHasCooldown.getCooldown(stack));
        } else {
            if (player.hasStatusEffect(ModEffects.ECHO_HIT)) {
                player.removeStatusEffect(ModEffects.ECHO_HIT);
            }
        }

        return ActionResult.PASS;
    }
}
