package com.bajookie.biotech.item;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent LEMON = new FoodComponent.Builder().snack().hunger(1).saturationModifier(0.25f).build();
    public static final FoodComponent ORANGE = new FoodComponent.Builder().snack().hunger(1).saturationModifier(0.25f).build();
    public static final FoodComponent CITRUSJUICE = new FoodComponent.Builder().hunger(5).saturationModifier(1.5f).build();
}
