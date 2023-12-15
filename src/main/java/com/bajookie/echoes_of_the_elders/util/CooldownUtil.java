package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.item.ICooldownReduction;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashMap;

public class CooldownUtil {
    public static float getCooldownReduction(PlayerEntity player) {
        var inv = player.getInventory();
        HashMap<String, Float> cooldowns = new HashMap<>();

        InventoryUtil.forEach(inv, stack -> {
            var i = stack.getItem();

            if (i instanceof ICooldownReduction iCooldownReduction) {
                cooldowns.put(iCooldownReduction.cooldownInstanceId(stack), iCooldownReduction.getCooldownReductionPercentage(stack));
            }
        });

        return cooldowns.values().stream().reduce(0f, Float::sum);
    }

    public static int getReducedCooldown(PlayerEntity player, Item item, int duration) {

        var minCd = 1;
        var affectCooldown = true;

        if (item instanceof IHasCooldown iHasCooldown) {
            minCd = iHasCooldown.getMinCooldown();
            affectCooldown = iHasCooldown.canReduceCooldown();
        }

        if (!affectCooldown) return duration;

        var cdr = CooldownUtil.getCooldownReduction(player);

        return Math.max(minCd, Math.round(duration * (1 - cdr)));
    }
}
