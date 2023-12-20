package com.bajookie.echoes_of_the_elders.client.animation;

import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AnimationUtil {
    private static final ArrayList<TickAnimation> animations = new ArrayList<>();

    public static final TickAnimation VITALITY_PUMP_HEARTBEAT_ANIMATION = registerAnimation(new TickAnimation(10));
    public static final TickAnimation VITALITY_PUMP_HEARTBEAT_AMBIENT_ANIMATION = registerAnimation(new RepeatingTickAnimation(60));
    public static final TickAnimation HUE_SHIFT_ANIMATION = registerAnimation(new RepeatingTickAnimation(180));


    private static TickAnimation registerAnimation(TickAnimation animation) {
        animations.add(animation);

        return animation;
    }

    public static void tick() {
        animations.forEach(TickAnimation::tick);
    }

    public static float sin(float n) {
        return (MathHelper.sin((n) * 2 * (float) Math.PI));
    }

    public static float sinPos(float n) {
        return (MathHelper.sin((n) * 2 * (float) Math.PI) + 1) / 2f;
    }

}
