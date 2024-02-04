package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.IcicleProjectile;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class IcicleStaff extends Item implements IArtifact, IHasCooldown, IStackPredicate {
    public IcicleStaff() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient){
            if (user.isSneaking()){
                EntityHitResult e = VectorUtil.raycastHit(10);
                if (e == null){
                    return super.use(world, user, hand);
                } else {
                    Entity entity = e.getEntity();
                    IcicleProjectile projectile = new IcicleProjectile(world,entity.getX(),entity.getY()+10,entity.getZ(),user,true);
                    world.spawnEntity(projectile);
                }
            } else {
                IcicleProjectile projectile = new IcicleProjectile(world,user.getX(),user.getY()+1.6,user.getZ(),user,false);
                projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 0.5f, 0f);
                world.spawnEntity(projectile);
            }

        }
        return super.use(world, user, hand);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return 20;
    }

    @Override
    public Model getBaseModel() {
        return Models.HANDHELD;
    }
}
