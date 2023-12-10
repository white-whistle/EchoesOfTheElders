package com.bajookie.echoes_of_the_elders.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ToolMaterialBuilder implements ToolMaterial {

    private int durability = 0;
    private float miningSpeedMultiplier = 0;
    private float attackDamage;
    private int miningLevel;
    private int enchantability;
    private Ingredient repairIngredient;

    public static ToolMaterialBuilder copyOf(ToolMaterial material) {
        return new ToolMaterialBuilder()
            .durability(material.getDurability())
            .miningSpeedMultiplier(material.getMiningSpeedMultiplier())
            .attackDamage(material.getAttackDamage())
            .miningLevel(material.getMiningLevel())
            .enchantability(material.getEnchantability())
            .repairIngredient(material.getRepairIngredient());
    }

    public ToolMaterialBuilder clone() {
        return ToolMaterialBuilder.copyOf(this);
    }

    public ToolMaterialBuilder durability(int durability) {
        this.durability = durability;
        return this;
    }

    public ToolMaterialBuilder miningSpeedMultiplier(float miningSpeedMultiplier) {
        this.miningSpeedMultiplier = miningSpeedMultiplier;
        return this;
    }

    public ToolMaterialBuilder attackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }

    public ToolMaterialBuilder miningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public ToolMaterialBuilder enchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public ToolMaterialBuilder repairIngredient(Ingredient repairIngredient) {
        this.repairIngredient = repairIngredient;
        return this;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeedMultiplier;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }
}
