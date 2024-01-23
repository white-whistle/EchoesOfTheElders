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
    public static Pair<Float,Float> directionToPitchYaw(Vec3d vec3d){
        float newPitch= (float) Math.atan(vec3d.x/(-vec3d.y));
        float newYaw = (float) (Math.sqrt((vec3d.x*vec3d.x)+(vec3d.y*vec3d.y))/vec3d.z);
        return new Pair<>(newPitch,newYaw);
    }
}
