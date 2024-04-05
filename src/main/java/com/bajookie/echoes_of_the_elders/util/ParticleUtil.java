package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@SuppressWarnings("unused")
public class ParticleUtil {

    public static Vector3f Y = new Vector3f(0, 1, 0);
    public static Vector3f Z = new Vector3f(0, 0, 1);
    public static Vector3f X = new Vector3f(1, 0, 0);

    public static void particleRing(World world, ParticleEffect particleType, Vector3f pos, Vector3f axis, Vector3f radius, Vector3f velocity, int segments) {

        var segDeg = 360 / (float) segments;


        for (int i = 0; i < segments; i++) {
            var iDeg = i * segDeg;
            float angleRadians = (float) Math.toRadians(iDeg);

            AxisAngle4f axisAngle = new AxisAngle4f(angleRadians, axis);
            var rotation = new Quaternionf(axisAngle);

            Vector3f rotatedRadius = new Vector3f(radius);
            rotatedRadius.rotate(rotation);

            Vector3f rotatedVelocity = new Vector3f(velocity);
            rotatedVelocity.rotate(rotation);

            world.addParticle(particleType, pos.x + rotatedRadius.x, pos.y + rotatedRadius.y, pos.z + rotatedRadius.z, rotatedVelocity.x, rotatedVelocity.y, rotatedVelocity.z);
        }


    }

    public static Vector3f zAxis(float length) {
        return new Vector3f(Z).mul(length);
    }

    public static Vector3f yAxis(float length) {
        return new Vector3f(Y).mul(length);
    }

    public static Vector3f xAxis(float length) {
        return new Vector3f(X).mul(length);
    }

}
