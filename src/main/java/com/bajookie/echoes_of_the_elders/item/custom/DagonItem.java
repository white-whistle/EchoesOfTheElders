package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.util.RayBox;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class DagonItem extends Item {
    public DagonItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            Vec3d userPos = user.getPos();
            Vec3d entityPos = entity.getPos();
            Vec3d diff = userPos.subtract(entityPos);
            Random r = new Random();
            for (double i = 1; i <= 20; i++) {
                user.getWorld().addParticle(ParticleTypes.LAVA, userPos.x - (diff.x * (i / 20)), userPos.y - (diff.y * (i / 20)) + 1, userPos.z - (diff.z * (i / 20)), 0.1*((float)r.nextInt(11)/10), 0.1*((float)r.nextInt(11)/10), 0.1*((float)r.nextInt(11)/10));
            }
            user.getItemCooldownManager().set(this, 20 * 20);
            entity.setOnFireFor(4);
            entity.damage(entity.getWorld().getDamageSources().magic(), 30f);

        }

        return super.useOnEntity(stack, user, entity, hand);
    }

//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        if (!user.getItemCooldownManager().isCoolingDown(this)) {
//            Entity entity = RayBox.getCollidedLivingEntity(user,world,10);
//            if(entity != null){
//                Vec3d userPos = user.getPos();
//                Vec3d entityPos = entity.getPos();
//                Vec3d diff = userPos.subtract(entityPos);
//                for (double i = 1; i <= 40; i++) {
//                    user.getWorld().addParticle(ParticleTypes.LAVA, userPos.x - (diff.x * (i / 40)), userPos.y - (diff.y * (i / 40)) + 1, userPos.z - (diff.z * (i / 40)), 0, 0.1, 0);
//                }
//                user.getItemCooldownManager().set(this, 20 * 20);
//                entity.setOnFireFor(4);
//                entity.damage(entity.getWorld().getDamageSources().magic(), 30f);
//            }
//        }
//        return super.use(world, user, hand);
//    }
}
