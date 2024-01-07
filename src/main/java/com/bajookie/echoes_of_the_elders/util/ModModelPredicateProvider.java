package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.IStackPredicate;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
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
        ModelPredicateProviderRegistry.register(ModItems.STASIS_STOPWATCH,new Identifier(MOD_ID,"tok"),
                ((stack, world, entity, seed) -> {
                    NbtCompound nbt = stack.getNbt();
                    if (nbt !=null){
                        int ticks = nbt.getInt("used_tick");
                        if (ticks ==0) return 0f;
                        if (ticks <=20) return 0.125f;
                        if (ticks <=40) return 0.125f*2;
                        if (ticks <=60) return 0.125f*3;
                        if (ticks <=80) return 0.125f*4;
                        if (ticks <=100) return 0.125f*5;
                        if (ticks <=120) return 0.125f*6;
                        if (ticks <=140) return 0.125f*7;
                        else return 0f;
                    }
                    return 0f;
                }));
    }
}
