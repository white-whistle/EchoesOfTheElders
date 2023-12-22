package com.bajookie.echoes_of_the_elders.entity;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public interface ModDamageTypes {
    RegistryKey<DamageType> DIVINE_ATTACK = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(EOTE.MOD_ID, "divine_attack"));

    static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        damageTypeRegisterable.register(DIVINE_ATTACK, new DamageType("player", 0.1f));
    }
}
