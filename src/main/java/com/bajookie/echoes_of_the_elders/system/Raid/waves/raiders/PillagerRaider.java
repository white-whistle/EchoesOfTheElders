package com.bajookie.echoes_of_the_elders.system.Raid.waves.raiders;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class PillagerRaider extends RaidEntityFeature<PillagerEntity>{
    public PillagerRaider(int minimumLevel, int step, int baseCount) {
        super(EntityType.PILLAGER, minimumLevel, step, baseCount);
    }

    @Override
    public PillagerEntity makeEntity(World world, int level) {
        var entity = this.entityType.create(world);
        this.equipEntity(entity,level);
        return entity;
    }

    @Override
    public void equipEntity(PillagerEntity entity, int level) {
        ItemStack stack = new ItemStack(Items.CROSSBOW);
        stack.addEnchantment(Enchantments.MULTISHOT,2);
        stack.addEnchantment(Enchantments.QUICK_CHARGE, 3);
        entity.equipStack(EquipmentSlot.MAINHAND,stack);
    }

    @Override
    public void buffEntity(PillagerEntity entity, int level) {

    }
}
