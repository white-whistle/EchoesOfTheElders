package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ModFoodComponents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StatFruit extends Item {
    private final Type type;
    public StatFruit(Type type) {
        super(new FabricItemSettings().food(ModFoodComponents.HEALTH_FOOD).maxCount(16));
        this.type = type;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player){
            if (!world.isClient){
                EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                instance.setBaseValue(instance.getBaseValue()+1);
            }
        }
        return super.finishUsing(stack, world, user);
    }
    public enum Type{
        HP,
        HUNGER,
        ABSORB,
        SPEED
    }
}
