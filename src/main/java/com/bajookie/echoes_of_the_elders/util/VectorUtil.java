package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

@SuppressWarnings("unused")
public class VectorUtil {
    public static Vector3f pitchYawRollToDirection(float pitch, float yaw, float roll) {
        float f = -MathHelper.sin(yaw * ((float) Math.PI / 180)) * MathHelper.cos(pitch * ((float) Math.PI / 180));
        float g = -MathHelper.sin((pitch + roll) * ((float) Math.PI / 180));
        float h = MathHelper.cos(yaw * ((float) Math.PI / 180)) * MathHelper.cos(pitch * ((float) Math.PI / 180));

        return new Vector3f(f, g, h);
    }

    public static Pair<Vec3d,Vec3d> getOrthogonalPlane(Vec3d vec) {
        Vec3d[] base = getStandardBase(vec);
        Vec3d u1 = new Vec3d(base[0].toVector3f());
        Vec3d u2 = base[1].subtract(u1.multiply((base[1].dotProduct(u1)) / (u1.dotProduct(u1))));
        Vec3d u3 = new Vec3d(base[2].toVector3f()).subtract(u1.multiply((base[2].dotProduct(u1))/(u1.dotProduct(u1))))
                .subtract(u2.multiply((base[2].dotProduct(u2))/(u2.dotProduct(u2))));
        return new Pair<>(u2,u3);
    }

    public static Vec3d[] getStandardBase(Vec3d vec) {
        Vec3d u1 = new Vec3d(vec.x, 0, 0);
        Vec3d u2 = new Vec3d(0, vec.y, 0);
        Vec3d u3 = new Vec3d(0, 0, vec.z);
        return new Vec3d[]{u1, u2, u3};
    }
    public static Pair<Vec3d,Vec3d> perpendicularPlaneFromVector(Vec3d vec){
        vec = vec.normalize();
        Vec3d v = new Vec3d(1, 0, 0);
        if (vec.crossProduct(v).equals(new Vec3d(0,0,0))) {
            v = new Vec3d(0, 1, 0);
        }

        Vec3d u = vec.crossProduct(v).normalize();
        Vec3d w = vec.crossProduct(u).normalize();
        return new Pair<>(u,w);
    }
    public static Pair<Float,Float> directionToPitchYaw(Vec3d vec3d){
        float newPitch= (float) Math.atan(vec3d.x/(-vec3d.y));
        float newYaw = (float) (Math.sqrt((vec3d.x*vec3d.x)+(vec3d.y*vec3d.y))/vec3d.z);
        return new Pair<>(newPitch,newYaw);
    }
}
