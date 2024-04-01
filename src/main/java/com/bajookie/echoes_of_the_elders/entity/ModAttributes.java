package com.bajookie.echoes_of_the_elders.entity;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModAttributes {
    public static final EntityAttribute GENERIC_EVASION = register("generic.evasion", new ClampedEntityAttribute("attribute.echoes_of_the_elders.name.generic.evasion", 100, 0, 200).setTracked(true));

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registries.ATTRIBUTE, ModIdentifier.string(id), attribute);
    }
}
