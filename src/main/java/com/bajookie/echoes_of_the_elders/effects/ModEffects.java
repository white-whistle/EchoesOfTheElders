package com.bajookie.echoes_of_the_elders.effects;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;


public class ModEffects {

    public static final StatusEffect DELAYED_LIGHTNING_EFFECT = registerStatusEffect("delayed_lightning_effect", DelayedEffect.create(StatusEffectCategory.HARMFUL, (instance, entity) -> {
        final int secondaryDelay = 20 * 5;
        var world = entity.getWorld();
        var level = instance.getAmplifier();

        if (entity instanceof PlayerEntity) return;
        if (!world.isClient()){
            ServerWorld worldServer = (ServerWorld) world;
            EntityType.LIGHTNING_BOLT.spawn(worldServer, entity.getBlockPos(), SpawnReason.TRIGGERED);

            if (level > 1) {
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.DELAYED_LIGHTNING_EFFECT, secondaryDelay, level - 1));
            }
        }
//        spawn lightning at entity
    }));

    private static StatusEffect registerStatusEffect(String name,StatusEffect effect){
        return Registry.register(Registries.STATUS_EFFECT,new Identifier(MOD_ID,name),effect);
    }
    public static void registerEffects(){
        EOTE.LOGGER.info("Registering status effects");
    }
}
