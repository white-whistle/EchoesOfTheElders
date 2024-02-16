package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.mixin.ClientWorldAccessor;
import com.bajookie.echoes_of_the_elders.mixin.ServerWorldAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityUtil {

    public static Entity getEntityByUUID(World world, UUID uuid) {
        if (world instanceof ServerWorld serverWorld) {
            return ((ServerWorldAccessor) serverWorld).invokeGetEntityLookup().get(uuid);
        } else if (world instanceof ClientWorld clientWorld) {
            return ((ClientWorldAccessor) clientWorld).invokeGetEntityLookup().get(uuid);
        }

        return null;
    }

    public enum Relation {
        FRIEND, FOE, UNKNOWN
    }

    public static Relation determineRelation(Entity entity) {
        if (entity instanceof HostileEntity) {
            return Relation.FOE;
        } else if (entity instanceof SlimeEntity) {
            return Relation.FOE;
        } else if (entity instanceof GhastEntity) {
            return Relation.FOE;
        } else if (entity instanceof AnimalEntity) {
            return Relation.FRIEND;
        } else if (entity instanceof SquidEntity) {
            return Relation.FRIEND;
        } else if (entity instanceof AmbientEntity) {
            return Relation.FRIEND;
        } else if (entity instanceof PassiveEntity) {
            return Relation.FRIEND;
        } else if (entity instanceof FishEntity) {
            return Relation.FRIEND;
        } else {
            return Relation.UNKNOWN;
        }
    }

    @Environment(EnvType.CLIENT)
    public static boolean showEntityOverlay(Entity entity, MinecraftClient client) {
        return entity instanceof LivingEntity
                && !(entity instanceof ArmorStandEntity)
                && !entity.isInvisibleTo(client.player)
                && entity != client.player
                && !entity.isSpectator();
    }
}
