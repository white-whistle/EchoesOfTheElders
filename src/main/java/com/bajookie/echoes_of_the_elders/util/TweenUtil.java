package com.bajookie.echoes_of_the_elders.util;

public class TweenUtil {
    public static float lerp(float a, float b, float progress) {
        return ((b - a) * progress) + a;
    }
}
