package com.bajookie.biotech.util;

import com.bajookie.biotech.BioTech;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
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
}
