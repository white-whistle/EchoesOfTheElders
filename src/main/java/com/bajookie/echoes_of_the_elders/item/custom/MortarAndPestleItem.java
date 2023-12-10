package com.bajookie.echoes_of_the_elders.item.custom;

import net.minecraft.item.Item;

public class MortarAndPestleItem extends Item {
    public MortarAndPestleItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }
    @Override
    public boolean canBeNested() {
        return false;
    }

}
