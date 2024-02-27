package com.bajookie.echoes_of_the_elders.item.ability;

import com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s.C2SCastItemStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public interface IHasSlotAbility {

    @Nullable
    Ability getAbility(EquipmentSlot equipmentSlot);

    static void handleCast(PlayerEntity player, EquipmentSlot slot) {
        if (player == null) return;

        var stack = player.getEquippedStack(slot);
        if (!(stack.getItem() instanceof IHasSlotAbility iHasSlotAbility)) return;

        var ability = iHasSlotAbility.getAbility(slot);
        if (ability == null) return;

        ability.cast(player.getWorld(), player, stack, false);

        if (player.getWorld().isClient) {
            C2SCastItemStack.send(EquipmentSlot.HEAD);
        }
    }
}
