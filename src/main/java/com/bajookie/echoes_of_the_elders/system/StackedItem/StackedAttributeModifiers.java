package com.bajookie.echoes_of_the_elders.system.StackedItem;

import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.function.Function;

public class StackedAttributeModifiers extends StackedLazyGenerator<Multimap<EntityAttribute, EntityAttributeModifier>> {
    public StackedAttributeModifiers(Function<Integer, Multimap<EntityAttribute, EntityAttributeModifier>> generator) {
        super(generator);
    }
}
