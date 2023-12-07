package com.bajookie.biotech.item.custom;

import com.bajookie.biotech.BioTech;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class GaleCoreItem extends Item {
    private final String use_id = new Identifier(MOD_ID,"use").toString();
    private final String active_id = new Identifier(MOD_ID,"active").toString();
    public GaleCoreItem() {
        super(new FabricItemSettings().maxCount(1).maxDamage(256));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (stack.hasNbt()){
            if (entity instanceof PlayerEntity user) {

                BioTech.LOGGER.info(String.valueOf(stack.getDamage()));
                if (!stack.getOrCreateNbt().contains(use_id)) {
                    stack.getOrCreateNbt().putInt(use_id, 0);
                    stack.getOrCreateNbt().putBoolean(active_id, false);
                }
                int usage = stack.getNbt().getInt(use_id);
                if (stack.getNbt().getBoolean(active_id)) {
                    if (usage > 10) {
                        stack.getNbt().putInt(use_id, 0);
                        stack.setDamage(stack.getDamage() + 2);
                        if (user.isFallFlying()) {
                            ItemStack itemStack = user.getStackInHand(user.getActiveHand());
                            if (!world.isClient) {
                                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, itemStack, user);
                                world.spawnEntity(fireworkRocketEntity);
                            }
                        }

                    } else {
                        stack.getNbt().putInt(use_id, usage + 1);
                    }
                } else {
                    if (usage > 9 && stack.getDamage() >0) {
                        stack.getNbt().putInt(use_id, 0);
                        stack.setDamage(stack.getDamage() - 1);
                    } else {
                        stack.getNbt().putInt(use_id, usage + 1);
                    }
                }
                if (stack.getDamage() >=256){
                    stack.getNbt().putBoolean(active_id,false);
                    user.getItemCooldownManager().set(this,20*30);
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            ItemStack stack = user.getStackInHand(hand);
            if (stack.hasNbt()) {
                if (stack.getOrCreateNbt().contains(active_id)) {
                    stack.getOrCreateNbt().putBoolean(active_id, !stack.getNbt().getBoolean(active_id));
                }
            } else {
                stack.getOrCreateNbt().putInt(use_id, 0);
                stack.getOrCreateNbt().putBoolean(active_id, false);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        BioTech.LOGGER.info("call");
        if (stack.hasNbt()){
            if (stack.getNbt().contains(active_id)){
                return stack.getNbt().getBoolean(active_id);
            } else {
                stack.getOrCreateNbt().putBoolean(active_id, false);
            }
        } else {
            stack.getOrCreateNbt().putBoolean(active_id, false);
        }
        return false;
    }
}
