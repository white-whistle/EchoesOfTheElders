package com.bajookie.biotech.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Collection;

public class RadiantLotusItem extends Item {
    public RadiantLotusItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getItemCooldownManager().set(this,20*60*5);
        Collection<StatusEffectInstance> effects = user.getStatusEffects();
        for(StatusEffectInstance effect: effects){
            if (!effect.getEffectType().isBeneficial()){
                user.removeStatusEffect(effect.getEffectType());
            }
        }
        return super.use(world, user, hand);
    }
}
