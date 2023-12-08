package com.bajookie.biotech.item.custom;

import com.bajookie.biotech.util.AuraUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AuraItem extends Item {

    protected final StatusEffect statusEffect;
    protected float range;
    protected int level;

    public AuraItem(Settings settings, StatusEffect statusEffect, float range, int level) {
        super(settings);

        this.statusEffect = statusEffect;
        this.range = range;
        this.level = level;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        AuraUtil.applyAuraEffect(world, entity.getPos(), statusEffect, this.range, this.level);
    }
}
