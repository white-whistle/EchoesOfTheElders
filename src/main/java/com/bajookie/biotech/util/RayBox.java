package com.bajookie.biotech.util;

import com.bajookie.biotech.BioTech;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class RayBox {

    @Nullable
    public static LivingEntity getCollidedLivingEntity(PlayerEntity user, World world, long reach) {
        MinecraftClient client = MinecraftClient.getInstance();

        Box searchBox = client.cameraEntity.getBoundingBox().stretch(user.getRotationVec(1f).multiply(reach));

        List<Entity> entities = world.getOtherEntities(client.cameraEntity, searchBox, entity -> true);
        if (entities.size() > 1) {
            Iterator<Entity> it = entities.iterator();
            while (it.hasNext()) {
                Entity entity = it.next();
                if (entity instanceof LivingEntity livingEntity) {
                    if (!(livingEntity instanceof PlayerEntity)) {
                        return livingEntity;
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }
    @Nullable
    public static LivingEntity getCollisionLivingEntityRT(long dist,LivingEntity entity,boolean includeFluids){
        MinecraftClient client = MinecraftClient.getInstance();
        Vec3d vecStart = entity.getEyePos();
        Vec3d vecEnd = null;
        HitResult result = client.cameraEntity.raycast(dist,1f,true);
        if (result.getType() == HitResult.Type.BLOCK){
            vecEnd = result.getPos();
            HitResult res = entity.getWorld().raycast(new RaycastContext(vecStart,vecEnd,RaycastContext.ShapeType.OUTLINE, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, entity));
            BioTech.LOGGER.info(""+res.getType());
        }else {
            return null;
        }

        return null;

    }
}
