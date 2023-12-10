package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.SwordItem;

import java.util.UUID;

public class AncientStoneSwordItem extends SwordItem {
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributes;

    protected static final UUID MAX_HEALTH_MODIFIER_ID = UUID.fromString("d907df76-860c-11ee-b9d1-0242ac120002");
    protected static final UUID MOVEMENT_SPEED_MODIFIER_ID = UUID.fromString("d907df76-860c-11ee-b9d1-0242ac120002");
    protected static final UUID ARMOUR_MODIFIER_ID = UUID.fromString("a2578f24-860e-11ee-b9d1-0242ac120002");
    protected static final UUID TOUGHNESS_MODIFIER_ID = UUID.fromString("b01f673a-860e-11ee-b9d1-0242ac120002");


    public AncientStoneSwordItem(float attackDamage, float attackSpeed, float armor, float toughness, float maxHealthModifierPercent, float movementSpeedModifier) {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 0, 0, new FabricItemSettings());

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(ARMOUR_MODIFIER_ID, "Weapon modifier", armor, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(TOUGHNESS_MODIFIER_ID, "Weapon modifier", toughness, EntityAttributeModifier.Operation.ADDITION));

        builder.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(MAX_HEALTH_MODIFIER_ID, "Weapon modifier", maxHealthModifierPercent, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(MOVEMENT_SPEED_MODIFIER_ID, "Weapon modifier", movementSpeedModifier, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));

        this.attributes = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) return this.attributes;

        return super.getAttributeModifiers(slot);
    }
}
