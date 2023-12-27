package com.bajookie.echoes_of_the_elders.client;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.util.Color;
import net.minecraft.client.MinecraftClient;

public class CustomOutlineColor {
    public static Color getOutlineColor() {

        var mc = MinecraftClient.getInstance();

        if (mc == null || mc.player == null) return null;

        var stack = mc.player.getMainHandStack();
        var item = stack.getItem();

        if (item == ModItems.REALITY_PICK) {
            return CustomItemColors.rainbow();
        }

        return null;
    }
}
