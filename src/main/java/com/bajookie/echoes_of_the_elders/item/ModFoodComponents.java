package com.bajookie.echoes_of_the_elders.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent EXPLORERS_FRUIT = new FoodComponent.Builder().hunger(10).saturationModifier(10f).statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,100*20,1),1).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED,100*20,1),1).build();
    public static final FoodComponent MINERS_FRUIT = new FoodComponent.Builder().hunger(10).saturationModifier(10f).statusEffect(new StatusEffectInstance(StatusEffects.LUCK,100*20,1),1).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE,100*20,1),1).build();
    public static final FoodComponent NETHER_FRUIT = new FoodComponent.Builder().hunger(10).saturationModifier(10f).statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,100*20,1),1).alwaysEdible().build();
    public static final FoodComponent HEALTH_FOOD = new FoodComponent.Builder().hunger(1).saturationModifier(1f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,2*20,2),1).alwaysEdible().build();
    public static final FoodComponent GENERAL_FOOD = new FoodComponent.Builder().hunger(1).saturationModifier(1f).alwaysEdible().build();
}
