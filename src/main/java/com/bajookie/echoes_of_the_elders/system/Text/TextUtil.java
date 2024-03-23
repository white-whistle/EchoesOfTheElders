package com.bajookie.echoes_of_the_elders.system.Text;

import com.bajookie.echoes_of_the_elders.client.tooltip.TooltipComponentSlot;
import com.bajookie.echoes_of_the_elders.system.Text.components.ModTextComponent;
import com.bajookie.echoes_of_the_elders.system.Text.components.ModTranslatableTextComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

@SuppressWarnings("unused")
public class TextUtil {
    private static final TextArgs EMPTY_ARGS = new TextArgs();

    private static float toFixed1(float v) {
        return Float.parseFloat(String.format("%.1f", v));
    }

    public static MutableText f1(float v) {
        return Math.floor(v) == v ? Text.translatable("number.echoes_of_the_elders.int", (int) v) : Text.translatable("number.echoes_of_the_elders.f1", toFixed1(v));
    }

    public static MutableText translatable(String key, TextArgs args) {
        return MutableText.of(new ModTranslatableTextComponent(key, null, args));
    }

    public static MutableText translatable(String key) {
        return MutableText.of(new ModTranslatableTextComponent(key, null, EMPTY_ARGS));
    }

    public static MutableText plain(String content) {
        return MutableText.of(new ModTextComponent(content, EMPTY_ARGS));
    }

    public static MutableText plain(String content, TextArgs args) {
        return MutableText.of(new ModTextComponent(content, args));
    }

    public static MutableText join(MutableText t1, MutableText t2) {
        return Text.translatable("tooltip.echoes_of_the_elders.join_spaced", t1, t2);
    }

    public static TooltipComponentSlot component(TooltipData data) {
        return new TooltipComponentSlot(data);
    }

}
