package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static net.minecraft.entity.projectile.ProjectileUtil.getEntityCollision;

@SuppressWarnings("unused")
public class VectorUtil {
    public static Vector3f pitchYawRollToDirection(float pitch, float yaw, float roll) {
        float f = -MathHelper.sin(yaw * ((float) Math.PI / 180)) * MathHelper.cos(pitch * ((float) Math.PI / 180));
        float g = -MathHelper.sin((pitch + roll) * ((float) Math.PI / 180));
        float h = MathHelper.cos(yaw * ((float) Math.PI / 180)) * MathHelper.cos(pitch * ((float) Math.PI / 180));

        return new Vector3f(f, g, h);
    }

    public static Pair<Vec3d, Vec3d> getOrthogonalPlane(Vec3d vec) {
        Vec3d[] base = getStandardBase(vec);
        Vec3d u1 = new Vec3d(base[0].toVector3f());
        Vec3d u2 = base[1].subtract(u1.multiply((base[1].dotProduct(u1)) / (u1.dotProduct(u1))));
        Vec3d u3 = new Vec3d(base[2].toVector3f()).subtract(u1.multiply((base[2].dotProduct(u1)) / (u1.dotProduct(u1))))
                .subtract(u2.multiply((base[2].dotProduct(u2)) / (u2.dotProduct(u2))));
        return new Pair<>(u2, u3);
    }

    public static Vec3d[] getStandardBase(Vec3d vec) {
        Vec3d u1 = new Vec3d(vec.x, 0, 0);
        Vec3d u2 = new Vec3d(0, vec.y, 0);
        Vec3d u3 = new Vec3d(0, 0, vec.z);
        return new Vec3d[]{u1, u2, u3};
    }

    public static Pair<Vec3d, Vec3d> perpendicularPlaneFromVector(Vec3d vec) {
        vec = vec.normalize();
        Vec3d v = new Vec3d(1, 0, 0);
        if (vec.crossProduct(v).equals(new Vec3d(0, 0, 0))) {
            v = new Vec3d(0, 1, 0);
        }

        Vec3d u = vec.crossProduct(v).normalize();
        Vec3d w = vec.crossProduct(u).normalize();
        return new Pair<>(u, w);
    }

    public static Pair<Float, Float> directionToPitchYaw(Vec3d vec3d) {
        float newPitch = (float) Math.atan(vec3d.x / (-vec3d.y));
        float newYaw = (float) (Math.sqrt((vec3d.x * vec3d.x) + (vec3d.y * vec3d.y)) / vec3d.z);
        return new Pair<>(newPitch, newYaw);
    }

    @Nullable
    public static EntityHitResult raycast(Entity from, double distance) {
        distance = distance * (300f / 17f);
        if (from != null) {
            Vec3d startPos = from.getEyePos();
            Vec3d vec3d2 = from.getRotationVec(1.0f);
            Vec3d endPos = startPos.add(vec3d2.x * distance, vec3d2.y * distance, vec3d2.z * distance);
            float f = 1.0f;
            Box box = from.getBoundingBox().stretch(vec3d2.multiply(distance)).expand(1.0, 1.0, 1.0);
            return ProjectileUtil.raycast(from, startPos, endPos, box, entity -> !entity.isSpectator() && entity.canHit() && entity.isAlive(), distance);
        }
        return null;
    }

    public static EntityHitResult raycastWithBlocks(Entity from, double distance) {
        var hit = ProjectileUtil.getCollision(from, entity -> !entity.isSpectator() && entity.canHit(), distance);

        if (hit == null) return null;
        if (hit.getType() != HitResult.Type.ENTITY) return null;

        return (EntityHitResult) hit;
    }

    @Nullable
    public static EntityHitResult raycast(LivingEntity caster, double range, float horizontalDispersion, float verticalDispersion) {
        Random r = Random.create();
        double yaw = caster.getYaw() + r.nextTriangular(0.0, 0.0172275 * (double) horizontalDispersion);
        double pitch = caster.getPitch() + r.nextTriangular(0.0, 0.0172275 * (double) verticalDispersion);
        float x = -MathHelper.sin(caster.getYaw() * 0.017453292F) * MathHelper.cos(caster.getPitch() * 0.017453292F);
        float y = -MathHelper.sin((caster.getPitch() + caster.getRoll()) * 0.017453292F);
        float z = MathHelper.cos(caster.getYaw() * 0.017453292F) * MathHelper.cos(caster.getPitch() * 0.017453292F);
        Vec3d vec3d = (new Vec3d(x, y, z)).normalize().multiply(range);
        Vec3d vec3d2 = caster.getEyePos();
        HitResult hit = getCollision(vec3d2, caster, entity -> !entity.isSpectator() && entity.canHit() && entity.isAlive(), vec3d, caster.getWorld());
        if (hit != null && (hit.getType() == HitResult.Type.ENTITY)) return (EntityHitResult) hit;
        return null;
    }
    private static HitResult getCollision(Vec3d pos, Entity entity, Predicate<Entity> predicate, Vec3d velocity, World world) {
        Vec3d vec3d = pos.add(velocity);
        HitResult hitResult = world.raycast(new RaycastContext(pos, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
        if (((HitResult) hitResult).getType() != HitResult.Type.MISS) {
            vec3d = ((HitResult) hitResult).getPos();
        }

        HitResult hitResult2 = ProjectileUtil.getEntityCollision(world, entity, pos, vec3d, entity.getBoundingBox().stretch(velocity).expand(1.0), predicate);
        if (hitResult2 != null) {
            hitResult = hitResult2;
        }

        return (HitResult) hitResult;
    }
}
