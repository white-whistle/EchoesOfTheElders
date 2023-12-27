package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

@SuppressWarnings("unused")
public class VectorUtil {
    public static Vector3f pitchYawRollToDirection(float pitch, float yaw, float roll) {
        float f = -MathHelper.sin(yaw * ((float) Math.PI / 180)) * MathHelper.cos(pitch * ((float) Math.PI / 180));
        float g = -MathHelper.sin((pitch + roll) * ((float) Math.PI / 180));
        float h = MathHelper.cos(yaw * ((float) Math.PI / 180)) * MathHelper.cos(pitch * ((float) Math.PI / 180));

        return new Vector3f(f, g, h);
    }
}
