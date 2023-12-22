package com.bajookie.echoes_of_the_elders.entity;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class ModDamageSources {

    public static DamageSource divineAttack(PlayerEntity attacker) {
        var damageSources = attacker.getDamageSources();
        return damageSources.create(ModDamageTypes.DIVINE_ATTACK, attacker);
    }

}
