package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.IStackPredicate;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModModelPredicateProvider {
    public static void registerModModels() {
        var stackedIdentifier = new Identifier(MOD_ID, "stack_level");

        ModItems.registeredModItems.forEach(item -> {
            if (item instanceof IStackPredicate iStackPredicate) {
                ModelPredicateProviderRegistry.register(
                        item,
                        stackedIdentifier,
                        (stack, world, entity, seed) -> iStackPredicate.getProgress(stack)
                );

            }
        });

        ModelPredicateProviderRegistry.register(ModItems.WITHER_SCALES_ITEM, new Identifier(MOD_ID, "on"),
                ((stack, world, entity, seed) -> {
                    if (entity instanceof PlayerEntity player) {
                        return player.getItemCooldownManager().isCoolingDown(stack.getItem()) ? 0f : 1f;
                    }
                    return 0f;
                }));

        ModelPredicateProviderRegistry.register(ModItems.CHAIN_LIGHTNING_ITEM, new Identifier(MOD_ID, "ready"),
                ((stack, world, entity, seed) -> {
                    if (entity instanceof PlayerEntity player) {
                        return player.getItemCooldownManager().isCoolingDown(stack.getItem()) ? 0f : 1f;
                    }
                    return 0f;
                }));
    }
}
