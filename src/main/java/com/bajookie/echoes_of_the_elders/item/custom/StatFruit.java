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
                switch (this.type){
                    case HP -> hpFruit(player);
                    case XP -> xpFruit(player);
                    case ATTACK -> attackFruit(player);
                    case SPEED -> speedFruit(player);
                }
            }
        }
        return super.finishUsing(stack, world, user);
    }
    private void xpFruit(PlayerEntity player){
        if (player.experienceLevel >30){
            for (int i = 0; i < 5; i++) {
                int val = player.getNextLevelExperience();
                player.addExperience(val);
            }
        } else {
            player.addExperience(1000);
        }

    }
    private void hpFruit(PlayerEntity player){
        EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (instance != null){
            instance.setBaseValue(instance.getBaseValue()+1);
        }
    }
    private void speedFruit(PlayerEntity player){
        EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (instance != null){
            if (instance.getBaseValue() <=3){
                instance.setBaseValue(instance.getBaseValue()+0.05d);
            }
        }
    }
    private void attackFruit(PlayerEntity player){
        EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (instance != null){
            instance.setBaseValue(instance.getBaseValue()+0.5d);
        }
    }
    public enum Type{
        HP,
        HUNGER,
        RESISTANCE,
        HASTE,
        REACH,
        SPEED,
        ATTACK,
        XP
    }
}
