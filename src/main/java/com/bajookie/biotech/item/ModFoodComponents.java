package com.bajookie.biotech.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent LEMON = new FoodComponent.Builder().snack().hunger(1).saturationModifier(0.25f).build();
    public static final FoodComponent ORANGE = new FoodComponent.Builder().snack().hunger(1).saturationModifier(0.25f).build();
    public static final FoodComponent CITRUSJUICE = new FoodComponent.Builder().hunger(5).saturationModifier(1.5f).build();
    public static final FoodComponent EXPLORERS_FRUIT = new FoodComponent.Builder().hunger(10).saturationModifier(10f).statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,100*20,1),1).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED,100*20,1),1).build();
}
