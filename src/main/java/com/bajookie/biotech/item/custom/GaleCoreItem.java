package com.bajookie.biotech.item.custom;

import com.bajookie.biotech.BioTech;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Vanishable;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
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

                if (!stack.getOrCreateNbt().contains(use_id)) {
                    stack.getOrCreateNbt().putInt(use_id, 0);
                    stack.getOrCreateNbt().putBoolean(active_id, false);
                }
                int usage = stack.getNbt().getInt(use_id);
                if (stack.getNbt().getBoolean(active_id)) {

                    Vec3d vec3d3 = Vec3d.ZERO;
                    if (user.isFallFlying()) {
                        world.addParticle(ParticleTypes.POOF,user.getX(),user.getY(),user.getZ(),0,0,0);
                        Vec3d vec3d = user.getRotationVector();
                        double d = 1.5;
                        double e = 0.1;
                        Vec3d vec3d2 = user.getVelocity();
                        user.setVelocity(vec3d2.add(vec3d.x * 0.1 + (vec3d.x * 1.5 - vec3d2.x) * 0.5, vec3d.y * 0.1 + (vec3d.y * 1.5 - vec3d2.y) * 0.5, vec3d.z * 0.1 + (vec3d.z * 1.5 - vec3d2.z) * 0.5));
                        vec3d3 = user.getHandPosOffset(Items.FIREWORK_ROCKET);
                    } else {
                        world.addParticle(ParticleTypes.POOF,user.getX(),user.getY(),user.getZ(),0,-2,0);
                        vec3d3 = Vec3d.ZERO;

                    }
                    if (usage > 10) {
                        stack.getNbt().putInt(use_id, 0);
                        stack.setDamage(stack.getDamage() + 2);

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
