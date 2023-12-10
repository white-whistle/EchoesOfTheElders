package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SquidEntity;

public class EntityUtil {

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

    public static boolean showEntityOverlay(Entity entity, MinecraftClient client) {
        return entity instanceof LivingEntity
                && !(entity instanceof ArmorStandEntity)
                && !entity.isInvisibleTo(client.player)
                && entity != client.player
                && !entity.isSpectator();
    }
}
